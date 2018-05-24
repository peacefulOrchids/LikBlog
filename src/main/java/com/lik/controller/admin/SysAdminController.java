package com.lik.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lik.entity.Link;
import com.lik.entity.Log;
import com.lik.entity.PageBean;
import com.lik.entity.Tag;
import com.lik.service.LinkService;
import com.lik.service.LogService;
import com.lik.service.TagService;
import com.lik.util.ResponseUtil;

/**
 * 系统Action类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SysAdminController {
	
	@Resource
	private LogService logService;

	
	/**
	 * 标签service
	 */
	@Resource
	private TagService tagService;

	
	/**
	 * 标签service
	 */
	@Resource
	private LinkService linkService;
	
	
	/**
	 * 刷新系统
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		map.put("pageSize",10);
		List<Tag> tagList = tagService.find(map);
		session.getServletContext().setAttribute("tagList", tagList);
		List<Log> logList = logService.find(map);
		session.getServletContext().setAttribute("logList", logList);
		List<Link> linkList = linkService.find(null);
		session.getServletContext().setAttribute("linkList", linkList);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(result,response);
		return null;
	}
	

}
