package com.lik.controller.admin;

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
import com.lik.entity.PageBean;
import com.lik.entity.Link;
import com.lik.entity.Link;
import com.lik.entity.User;
import com.lik.service.CommentService;
import com.lik.service.LogService;
import com.lik.service.LinkService;
import com.lik.util.DateJsonValueProcessor;
import com.lik.util.ObjectJsonValueProcessor;
import com.lik.util.PageUtil;
import com.lik.util.ResponseUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {
	@Resource
	private LinkService linkService;

	private int page=1;//当前页
	private int pageSize=10;//一页可有多少个
	/**查看所有用户*/
	@RequestMapping("/list")
	public String list(Link Link,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		if(Link!=null){
			if(StringUtil.isNotEmpty(Link.getLinkname())){
				map.put("linkname",StringUtil.formatLike(Link.getLinkname()));
			}
			if(StringUtil.isNotEmpty(Link.getLinkurl())){
				map.put("linkurl",Link.getLinkurl());
			}
		}
		if(page!=null){
			this.page = Integer.parseInt(page);
		}else{
			this.page=1;
		}
		if(rows!=null){
			this.pageSize = Integer.parseInt(rows);
		}else{
			this.pageSize = 10;
		}
		request.setAttribute("s_Link", Link);
		PageBean pageBean = new PageBean(this.page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<Link> LinkList = linkService.find(map);
		long total = linkService.countLink(map);
		/**Json配置*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:排除*/
		//jsonConfig.setExcludes(new String[]{"orderList"});
		/**给json值加工一下*/
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));  
		JSONArray array=JSONArray.fromObject(LinkList,jsonConfig);
		/**json对象*/
		JSONObject resultJson = new JSONObject();
		/**total:总记录数量,rows:数据记录数组*/
		resultJson.put("rows", array);
		resultJson.put("total", total);
		/**向页面写入*/
		ResponseUtil.write(resultJson,response);//向页面输出
		return null;
	}
	@RequestMapping("/allLink")
	public String allLink(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Link> LinkList = linkService.find(null);
		/**Json配置*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:排除*/
		//jsonConfig.setExcludes(new String[]{"orderList"});
		/**给json值加工一下*/
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));  
		JSONArray array=JSONArray.fromObject(LinkList,jsonConfig);
		/**json对象*/
		/**total:总记录数量,rows:数据记录数组*/
		/**向页面写入*/
		ResponseUtil.write(array,response);//向页面输出
		return null;
	}
	@RequestMapping("/saveLink")
	public String saveLink(Link Link,HttpServletRequest request,HttpServletResponse response)throws Exception{
		int result = 0;
		if(Link.getId()!=null){
			result = linkService.update(Link);
		}else{
			result = linkService.add(Link);
		}
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
	}
	@RequestMapping("/delLink")
	public String delLinks(String ids,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] LinksIds = ids.split(",");
		for(String id:LinksIds){
			linkService.delete(Integer.parseInt(id));
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson, response);//向页面输出
		return null;
	}
	
}
