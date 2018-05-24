package com.lik.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lik.dao.LinkDao;
import com.lik.entity.Link;
import com.lik.service.LinkService;
@Service("linkService")
public class LinkServiceImpl implements LinkService {
	@Resource
	LinkDao linkDao;
	public List<Link> find(Map<String, Object> map) {
		return linkDao.find(map);
	}
	public Link findById(Integer id) {
		return linkDao.findById(id);
	}
	public Long countLink(Map<String, Object> map) {
		return linkDao.countLink(map);
	}
	public int add(Link Link) {
		return linkDao.add(Link);
	}
	public int update(Link Link) {
		return linkDao.update(Link);
	}
	public int delete(Integer id) {
		return linkDao.delete(id);
	}
}
