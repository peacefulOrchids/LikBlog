package com.lik.controller.admin;

import java.text.SimpleDateFormat;
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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lik.entity.PageBean;
import com.lik.entity.Message;
import com.lik.entity.User;
import com.lik.service.MessageService;
import com.lik.util.DateJsonValueProcessor;
import com.lik.util.DateUtil;
import com.lik.util.PageUtil;
import com.lik.util.ResponseUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/admin/message")
public class MessageAdminController {
	@Resource
	private MessageService messageService;

	private int page=1;//��ǰҳ
	private int pageSize=10;//һҳ���ж��ٸ�

	//springmvc���ݴ�ǰ̨����,�ַ���ת�������ڶ��� �������Ļᱨ��
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:���������ֵ,false����Ϊ��ֵ
	}
	/**�鿴�����û�*/
	@RequestMapping("/list")
	public String list(Message message,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		if(message!=null){
			if(StringUtil.isNotEmpty(message.getMsgName())){
				map.put("msgName", "%"+message.getMsgName()+"%");
			}
			if(StringUtil.isNotEmpty(message.getContent())){
				map.put("content", "%"+message.getContent()+"%");
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
		request.setAttribute("s_message", message);
		PageBean pageBean = new PageBean(this.page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<Message> messageList = messageService.find(map);
		long total = messageService.countMessage(map);
		/**Json����*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:�ų�*/
		jsonConfig.setExcludes(new String[]{"orderList"});
		/**��jsonֵ�ӹ�һ��*/
		//jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
		JSONArray array=JSONArray.fromObject(messageList,jsonConfig);
		/**json����*/
		JSONObject resultJson = new JSONObject();
		/**total:�ܼ�¼����,rows:���ݼ�¼����*/
		resultJson.put("rows", array);
		resultJson.put("total", total);
		/**��ҳ��д��*/
		ResponseUtil.write(resultJson,response);//��ҳ�����
		return null;
	}

	@RequestMapping("/saveMessage")
	public String saveMessage(Message message,String veryCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		int result = 0;
		result = messageService.update(message);
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
	@RequestMapping("/reply")
	public String reply(Message message,HttpServletRequest request,HttpServletResponse response)throws Exception{
		int result = 0;
		message.setReplyTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
		result = messageService.update(message);
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
	@RequestMapping("/delMessage")
	public String delMessage(String ids,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] MessageIds = ids.split(",");
		for(String MessageId:MessageIds){
			messageService.delete(Integer.parseInt(MessageId));
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson, response);//��ҳ�����
		return null;
	}
	
}
