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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lik.entity.Comment;
import com.lik.entity.PageBean;
import com.lik.entity.Log;
import com.lik.entity.Tag;
import com.lik.entity.User;
import com.lik.lucene.LogIndex;
import com.lik.service.CommentService;
import com.lik.service.LogService;
import com.lik.util.DateJsonValueProcessor;
import com.lik.util.DateUtil;
import com.lik.util.ObjectJsonValueProcessor;
import com.lik.util.PageUtil;
import com.lik.util.ResponseUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/admin/log")
public class LogsAdminController {
	@Resource
	private LogService logService;

	private LogIndex logIndex = new LogIndex();
	@Resource
	private CommentService commentService;
	private int page=1;//��ǰҳ
	private int pageSize=10;//һҳ���ж��ٸ�
	/**�鿴������־*/
	@RequestMapping("/list")
	public String list(Log log,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		if(log!=null){
			if(StringUtil.isNotEmpty(log.getTitle())){
				map.put("title",StringUtil.formatLike(log.getTitle()));
			}
			if(log.getTag()!=null){
				if(log.getTag().getId()>0){
					map.put("tid",log.getTag().getId());
				}
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
		request.setAttribute("s_log", log);
		PageBean pageBean = new PageBean(this.page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<Log> logList = logService.find(map);
		long total = logService.countLogs(map);
		/**Json����*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:�ų�*/
		jsonConfig.setExcludes(new String[]{"commentList"});
		/**��jsonֵ�ӹ�һ��*/
		//jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));  
		jsonConfig.registerJsonValueProcessor(Tag.class,new ObjectJsonValueProcessor(new String[]{"id","name"}, Tag.class)); 
		JSONArray array=JSONArray.fromObject(logList,jsonConfig);
		/**json����*/
		JSONObject resultJson = new JSONObject();
		/**total:�ܼ�¼����,rows:���ݼ�¼����*/
		resultJson.put("rows", array);
		resultJson.put("total", total);
		/**��ҳ��д��*/
		ResponseUtil.write(resultJson,response);//��ҳ�����
		return null;
	}
	@RequestMapping("/saveLogs")
	public String saveLogs(Log log,HttpServletRequest request,HttpServletResponse response)throws Exception{
		int result = 0;
		if(log.getId()>0){
			result = logService.update(log);
			logIndex.updateIndex(log);//�޸�����
		}else{
			log.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			result = logService.add(log);
			logIndex.addIndex(log);//�������
		}
		/**json����*/
		JSONObject resultJson = new JSONObject();
		/**total:�ܼ�¼����,rows:���ݼ�¼����*/
		if(result>0){
			resultJson.put("success",true);
		}else{
			resultJson.put("errorMsg","�����쳣");
		}
		/**��ҳ��д��*/
		ResponseUtil.write(resultJson, response);//��ҳ�����
		return null;
	}
	@RequestMapping("/preSave")
	public String preSave(Log log,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log = logService.findById(log.getId());
		request.setAttribute("mylog", log);
		return "/admin/writeLog";
	}
	
	@RequestMapping("/delLogs")
	public String delLogs(String ids,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] LogsIds = ids.split(",");
		for(String id:LogsIds){
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("lid", id);
			if(commentService.find(map).size()>0){
				JSONObject resultJson = new JSONObject();
				resultJson.put("errorMsg","����־�»�������,���Ƚ�����ɾ��!");
				ResponseUtil.write(resultJson, response);//��ҳ�����
				return null;
			}
			logService.delete(Integer.parseInt(id));
			logIndex.deleteIndex(id);
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson, response);//��ҳ�����
		return null;
	}
}
