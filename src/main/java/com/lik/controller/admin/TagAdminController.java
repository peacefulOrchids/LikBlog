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
import com.lik.entity.Tag;
import com.lik.entity.Tag;
import com.lik.entity.User;
import com.lik.service.CommentService;
import com.lik.service.LogService;
import com.lik.service.TagService;
import com.lik.util.DateJsonValueProcessor;
import com.lik.util.ObjectJsonValueProcessor;
import com.lik.util.PageUtil;
import com.lik.util.ResponseUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/admin/tag")
public class TagAdminController {
	@Resource
	private TagService tagService;

	@Resource
	private LogService logService;

	private int page=1;//��ǰҳ
	private int pageSize=10;//һҳ���ж��ٸ�
	/**�鿴�����û�*/
	@RequestMapping("/list")
	public String list(Tag tag,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		if(tag!=null){
			if(StringUtil.isNotEmpty(tag.getName())){
				map.put("name",StringUtil.formatLike(tag.getName()));
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
		request.setAttribute("s_tag", tag);
		PageBean pageBean = new PageBean(this.page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<Tag> tagList = tagService.find(map);
		long total = tagService.countTag(map);
		/**Json����*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:�ų�*/
		//jsonConfig.setExcludes(new String[]{"orderList"});
		/**��jsonֵ�ӹ�һ��*/
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));  
		JSONArray array=JSONArray.fromObject(tagList,jsonConfig);
		/**json����*/
		JSONObject resultJson = new JSONObject();
		/**total:�ܼ�¼����,rows:���ݼ�¼����*/
		resultJson.put("rows", array);
		resultJson.put("total", total);
		/**��ҳ��д��*/
		ResponseUtil.write(resultJson,response);//��ҳ�����
		return null;
	}
	@RequestMapping("/allTag")
	public String allTag(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Tag> tagList = tagService.find(null);
		/**Json����*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:�ų�*/
		//jsonConfig.setExcludes(new String[]{"orderList"});
		/**��jsonֵ�ӹ�һ��*/
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));  
		JSONArray array=JSONArray.fromObject(tagList,jsonConfig);
		/**json����*/
		/**total:�ܼ�¼����,rows:���ݼ�¼����*/
		/**��ҳ��д��*/
		ResponseUtil.write(array,response);//��ҳ�����
		return null;
	}
	@RequestMapping("/saveTag")
	public String saveTag(Tag tag,HttpServletRequest request,HttpServletResponse response)throws Exception{
		int result = 0;
		if(tag.getId()>0){
			result = tagService.update(tag);
		}else{
			result = tagService.add(tag);
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
	@RequestMapping("/delTag")
	public String delTags(String ids,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] TagsIds = ids.split(",");
		for(String id:TagsIds){
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("tid",id);
			if(logService.find(map).size()>0){
				JSONObject resultJson = new JSONObject();
				resultJson.put("errorMsg","�ñ�ǩ�»�����־,���Ƚ���־ɾ��!");
				ResponseUtil.write(resultJson, response);//��ҳ�����
				return null;
			}
			tagService.delete(Integer.parseInt(id));
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson, response);//��ҳ�����
		return null;
	}
	
}
