package com.lik.controller;

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
import com.lik.entity.User;
import com.lik.service.CommentService;
import com.lik.service.CommentService;
import com.lik.service.LogService;
import com.lik.util.DateJsonValueProcessor;
import com.lik.util.DateUtil;
import com.lik.util.PageUtil;
import com.lik.util.ResponseUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private CommentService commentService;
	@Resource
	private LogService logService;
	@RequestMapping("/saveComment")
	public String saveComments(Comment comment,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(comment.getFid()==0){
			comment.setFid(null);
		}
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("id", comment.getLog().getId());
		map.put("hit", "∆¿¬€¡ø");
		logService.addClickOrReply(map);
		comment.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
		commentService.add(comment);
		return "redirect:/log/info/"+comment.getLog().getId()+".html";
	}
	
}
