package com.lik.controller;

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
@RequestMapping("/message")
public class MessageController {
	@Resource
	private MessageService messageService;

	private int page=1;//当前页
	private int pageSize=10;//一页可有多少个

	//springmvc数据从前台过来,字符串转化成日期对象 否则增改会报错
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值,false不能为空值
	}
	/**查看所有用户*/
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
		request.setAttribute("msgList", messageList);
		request.setAttribute("pageCode", PageUtil.genPagination("list.do", total,this.page, pageSize,null));
		request.setAttribute("mainPage", "foreground/blogger/message.jsp");
		return "main";
	}

	@RequestMapping("/saveMessage")
	public String saveMessage(Message message,String veryCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		message.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
		messageService.add(message);
		list(null, null, null, request, response);
		return "main";
	}
}
