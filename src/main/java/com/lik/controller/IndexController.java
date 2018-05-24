package com.lik.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lik.entity.Log;
import com.lik.entity.PageBean;
import com.lik.service.LogService;
import com.lik.util.ImageCode;
import com.lik.util.PageUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/")
public class IndexController {
	@Resource
	private LogService logService;
	
	/**
	 * 请求主页
	 */
	@RequestMapping("/index")
	public ModelAndView index(Log log,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		String param = "";
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		if (StringUtil.isEmpty(rows)) {
			rows = "3";
		}
		Map<String , Object> map = new HashMap<String, Object>();
		if(log!=null){
			if(StringUtil.isNotEmpty(log.getTitle())){
				map.put("title",StringUtil.formatLike(log.getTitle()));
				param += "title="+log.getTitle();
			}
			if(log.getTag()!=null){
				if(log.getTag().getId()>0){
					map.put("tid",log.getTag().getId());
					param += "tag.id="+log.getTag().getId();
				}
			}
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<Log> logList = logService.find(map);
		for (Log l:logList) {
			List<String> imageList = l.getImageList();
			String logInfo = l.getContent();
			Document doc = Jsoup.parse(logInfo);
			Elements jpgs = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
			for (int i = 0; i < jpgs.size(); i++) {
				Element jpg = jpgs.get(i);
				imageList.add(jpg.toString());
				if (i==2) {
					break;
				}
			}
			l.setImageList(imageList);
		}
		mav.addObject("logList", logList);
		mav.addObject("mainPage", "foreground/blog/logs.jsp");
		long total = logService.countLogs(map);
		mav.addObject("pageCode", PageUtil.genPagination("index.html", total,Integer.parseInt(page), Integer.parseInt(rows),param));
		mav.setViewName("main");
		return mav;
	}
	 /**
	  * 请求赞助
	  */
	@RequestMapping("/sponsor")
	public ModelAndView sponsor(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainPage", "foreground/blogger/sponsor.jsp");
		mav.setViewName("main");
		return mav;
	}
	 /**
	  * 请求关于我
	  */
	@RequestMapping("/sky")
	public ModelAndView sky(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainPage", "foreground/blogger/sky.jsp");
		mav.setViewName("main");
		return mav;
	}
	 /**
	  * 请求关于本站
	  */
	@RequestMapping("/about")
	public ModelAndView about(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainPage", "foreground/blogger/about.jsp");
		mav.setViewName("main");
		return mav;
	}
	 /**
	  * 生成验证码
	 * @throws IOException 
	  */
	@RequestMapping("/image")
	public void image(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		OutputStream os = response.getOutputStream();
		String vcode = ImageCode.getImageCode(60, 20, os);
		session.setAttribute("sRand", vcode);
		os.flush();
		os.close();
	}
}
