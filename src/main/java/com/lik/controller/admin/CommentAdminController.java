package com.lik.controller.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lik.entity.Comment;
import com.lik.entity.Log;
import com.lik.entity.PageBean;
import com.lik.entity.Comment;
import com.lik.entity.Tag;
import com.lik.entity.User;
import com.lik.service.CommentService;
import com.lik.service.CommentService;
import com.lik.service.LogService;
import com.lik.util.DateJsonValueProcessor;
import com.lik.util.DateUtil;
import com.lik.util.ObjectJsonValueProcessor;
import com.lik.util.PageUtil;
import com.lik.util.ResponseUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

	@Resource
	private CommentService commentService;
	@Resource
	private LogService logService;
	private int page=1;//当前页
	private int pageSize=10;//一页可有多少个
	/**查看所有用户*/
	@RequestMapping("/list")
	public String list(Comment comment,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		if(comment!=null){
			if(StringUtil.isNotEmpty(comment.getCname())){
				map.put("cname",StringUtil.formatLike(comment.getCname()));
			}
			if(StringUtil.isNotEmpty(comment.getContent())){
				map.put("content",StringUtil.formatLike(comment.getContent()));
			}
			if(comment.getLog()!=null){
				if(StringUtil.isNotEmpty(comment.getLog().getTitle())){
					map.put("logTitle",StringUtil.formatLike(comment.getLog().getTitle()));
				}
			}
		}
		map.put("all",1);
		if(page!=null){
			this.page = Integer.parseInt(page);
		}
		if(rows!=null){
			this.pageSize = Integer.parseInt(rows);
		}
		request.setAttribute("s_comments", comment);
		PageBean pageBean = new PageBean(this.page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<Comment> commentList = commentService.find(map);
		long total = commentService.countComments(map);
		/**Json配置*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:排除*/
		//jsonConfig.setExcludes(new String[]{"orderList"});
		/**给json值加工一下*/
		//jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));  
		jsonConfig.registerJsonValueProcessor(Log.class,new ObjectJsonValueProcessor(new String[]{"id","title"}, Log.class)); 
		JSONArray array=JSONArray.fromObject(commentList,jsonConfig);
		/**json对象*/
		JSONObject resultJson = new JSONObject();
		/**total:总记录数量,rows:数据记录数组*/
		resultJson.put("rows", array);
		resultJson.put("total", total);
		/**向页面写入*/
		ResponseUtil.write(resultJson,response);//向页面输出
		return null;
	}
	@RequestMapping("/saveComment")
	public String saveComments(Comment comment,HttpServletRequest request,HttpServletResponse response)throws Exception{
		int result = 0;
		if(comment.getId()!=null&&comment.getId()>0){
			result = commentService.update(comment);
			/**json对象*/
			JSONObject resultJson = new JSONObject();
			/**total:总记录数量,rows:数据记录数组*/
			if(result>0){
				resultJson.put("success",true);
			}else{
				resultJson.put("errorMsg","发送异常");
			}
			/**向页面写入*/
			ResponseUtil.write(resultJson, response);//向页面输出
			return null;
		}else{
			if(comment.getFid()==0){
				comment.setFid(null);
			}
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("id", comment.getLog().getId());
			map.put("hit", "评论量");
			logService.addClickOrReply(map);
			comment.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			result = commentService.add(comment);
		}
		return "redirect:/log/info/"+comment.getLog().getId()+".html";
	}
	@RequestMapping("/delComment")
	public String delComments(String ids,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] CommentsIds = ids.split(",");
		for(String id:CommentsIds){
			if (commentService.findByCid(Integer.parseInt(id)) != null) {
				JSONObject resultJson = new JSONObject();
				resultJson.put("errorMsg","该评论下有子评论,请先将子评论删完!");
				ResponseUtil.write(resultJson, response);//向页面输出
				return null;
			}else{
				Comment com = commentService.findById(Integer.parseInt(id));
				Map<String , Object> map = new HashMap<String, Object>();
				map.put("id", com.getLog().getId());
				map.put("hit", "评论量");
				logService.deleteClickOrReply(map);
				commentService.delete(Integer.parseInt(id));
			}
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson, response);//向页面输出
		return null;
	}
	
}
