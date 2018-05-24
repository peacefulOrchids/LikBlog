package com.lik.service.impl;

import java.util.List;
import java.util.Map;

import com.lik.dao.LogDao;
import com.lik.dao.UserDao;
import com.lik.entity.Log;
import com.lik.service.LogService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service("logService")
public class LogServiceImpl implements LogService {

	@Resource
	private LogDao logsDao;
	public List<Log> find(Map<String, Object> map) {
		return logsDao.find(map);
	}

	
	public Log findById(Integer id) {
		return logsDao.findById(id);
	}

	
	public Long countLogs(Map<String, Object> map) {
		return logsDao.countLogs(map);
	}

	
	public int add(Log logs) {
		return logsDao.add(logs);
	}

	
	public int update(Log logs) {
		return logsDao.update(logs);
	}

	public int delete(Integer id) {
		return logsDao.delete(id);
	}


	public int addClickOrReply(Map<String,Object> map) {
		return logsDao.addClickOrReply(map);
	}


	public Log findIdLast(Integer id) {
		return logsDao.findIdLast(id);
	}


	public Log findIdNext(Integer id) {
		return logsDao.findIdNext(id);
	}


	public int deleteClickOrReply(Map<String, Object> map) {
		return logsDao.deleteClickOrReply(map);
	}

}
