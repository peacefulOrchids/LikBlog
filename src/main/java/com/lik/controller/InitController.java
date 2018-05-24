package com.lik.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.lik.entity.Link;
import com.lik.entity.Log;
import com.lik.entity.Tag;
import com.lik.service.LinkService;
import com.lik.service.LogService;
import com.lik.service.TagService;


@Component
public class InitController implements ServletContextListener,ApplicationContextAware{
	// π”√æ≤Ã¨–ﬁ Œ
	private static ApplicationContext applicationContext;
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext application = servletContextEvent.getServletContext();			
		TagService tagService = (TagService) applicationContext.getBean("tagService");
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		map.put("pageSize",10);
		List<Tag> tagList = tagService.find(map);
		application.setAttribute("tagList", tagList);
		LogService logService = (LogService) applicationContext.getBean("logService");
		List<Log> logList = logService.find(map);
		application.setAttribute("logList", logList);
		
		LinkService linkService = (LinkService) applicationContext.getBean("linkService");
		List<Link> linkList = linkService.find(null);
		application.setAttribute("linkList", linkList);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		//System.out.println("1");
		this.applicationContext = applicationContext;
	}
	
}