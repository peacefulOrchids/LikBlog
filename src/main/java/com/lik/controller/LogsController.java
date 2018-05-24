package com.lik.controller;

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
@RequestMapping("/log")
public class LogsController {
	@Resource
	private LogService logService;

	private LogIndex logIndex = new LogIndex();
	
	@Resource
	private CommentService commentService;
	private int page=1;//当前页
	private int pageSize=10;//一页可有多少个
	/**查看所有日志*/
	@RequestMapping("/list")
	public String list(Log log,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		String param = "";
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
		request.getSession().setAttribute("logList", logList);
		request.setAttribute("mainPage", "foreground/blog/logs.jsp");
		request.setAttribute("pageCode", PageUtil.genPagination("list.do", total,this.page, pageSize,param));
		return "main";
	}

	@RequestMapping("/tag/{tid}")
	public ModelAndView findByTid(@PathVariable("tid")Integer tid,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		String param = "";
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		if (StringUtil.isEmpty(rows)) {
			rows = "3";
		}
		Map<String , Object> map = new HashMap<String, Object>();
		if(tid!=null){
			map.put("tid",tid);
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
		mav.addObject("pageCode", PageUtil.genPagination(tid+".html", total,Integer.parseInt(page), Integer.parseInt(rows),param));
		mav.setViewName("main");
		return mav;
	}
	@RequestMapping("/info/{id}")
	public String info(@PathVariable("id")Integer id,@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
		this.pageSize=3;
		if(page!=null){
			this.page = Integer.parseInt(page);
		}
		if(rows!=null){
			this.pageSize = Integer.parseInt(rows);
		}
		PageBean pageBean = new PageBean(this.page, pageSize);
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("lid", id);
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<Comment> commentList = commentService.find(map);
		Log log = logService.findById(id);
		request.setAttribute("commentList", commentList);
		String param = "id="+id;
		request.setAttribute("mainPage", "foreground/blog/logInfo.jsp");
		request.setAttribute("mylog", log);
		long total = commentService.countComments(map);
		StringBuffer nextOrLast = new StringBuffer();
		getLastLog(nextOrLast, id);
		nextOrLast.append("---");
		getNextLog(nextOrLast, id);
		request.setAttribute("nextOrLast", nextOrLast);
		request.setAttribute("pageCode", PageUtil.genPagination("log/info/"+id+".html", total,this.page, pageSize,param));
		return "main";
	}
	/**
	 * 得到上一篇
	 * @param nextOrLast
	 * @param id
	 */
	public void getLastLog(StringBuffer nextOrLast,Integer id){
		Log lastLog = logService.findIdLast(id);
		if (lastLog!=null) {
			nextOrLast.append("<a href='log/info/"+lastLog.getId()+".html'>上一篇日志:+"+lastLog.getTitle()+"</a>");
		}else{
			nextOrLast.append("没有上一篇了");
		}
	}
	/**
	 * 得到下一篇
	 * @param nextOrLast
	 * @param id
	 */
	public void getNextLog(StringBuffer nextOrLast,Integer id){
		Log nextLog = logService.findIdNext(id);
		if (nextLog!=null) {
			nextOrLast.append("<a href='log/info/"+nextLog.getId()+".html'>下一篇日志:"+nextLog.getTitle()+"</a>");
		}else{
			nextOrLast.append("没有下一篇了");
		}
	}
	/**
	 * 添加浏览量
	 * @throws Exception 
	 */
	@RequestMapping("/addClick")
	public String addClick(Integer id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("hit", "点击量");
		logService.addClickOrReply(map);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson, response);//向页面输出
		return null;
	}
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="q",required=false)String q,@RequestParam(value="page",required=false)String page,HttpServletRequest request) throws Exception{
		int pageSize = 3;
		if(StringUtil.isEmpty(page)){
			page = "1";
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("pageTitle","搜索关键字'"+q+"'结果页面");
		mav.addObject("mainPage","foreground/blog/result.jsp");
		List<Log> logList = logIndex.searchBlog(q);
		Integer toIndex = logList.size()>=Integer.parseInt(page)*pageSize?Integer.parseInt(page)*pageSize:logList.size();
		mav.addObject("logList",logList.subList(((Integer.parseInt(page)-1)*pageSize), toIndex));
		mav.addObject("q",q);
		mav.addObject("pageCode",this.genUpAndDownPageCode(Integer.parseInt(page),logList.size(),q,pageSize,request.getContextPath()));
		mav.addObject("resultTotal",logList.size());
		mav.setViewName("main");;
		return mav;
	}

	private String genUpAndDownPageCode(int page, int totalNum, String q,
			int pageSize, String projectContext) {
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		if (totalNum==0) {
			return "";
		}else{
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager'>");
			if (page>1) {
				pageCode.append("<li><a href='"+projectContext+"/log/q.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='javascript:void(0);'>上一页</a></li>");
			}
			if(page<totalPage){
				pageCode.append("<li><a href='"+projectContext+"/log/q.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='javascript:void(0);'>下一页</a></li>");
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}
	@RequestMapping("/saveLogs")
	public String saveLogs(Log log,HttpServletRequest request,HttpServletResponse response)throws Exception{
		String title = new String(log.getTitle().getBytes("gb2312"),"utf-8");
		int result = 0;
		if(log.getId()>0){
			result = logService.update(log);
			logIndex.updateIndex(log);//修改索引
		}else{
			log.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			result = logService.add(log);
			logIndex.addIndex(log);//添加索引
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
}
