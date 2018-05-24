package com.lik.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lik.dao.TagDao;
import com.lik.entity.Tag;
import com.lik.service.TagService;

@Service("tagService")
public class TagServiceImpl implements TagService {

	@Resource
	private TagDao tagDao;
	
	public List<Tag> find(Map<String, Object> map) {
		return tagDao.find(map);
	}

	
	public Tag findById(Integer id) {
		return tagDao.findById(id);
	}

	
	public Long countTag(Map<String, Object> map) {
		return tagDao.countTag(map);
	}

	
	public int add(Tag tag) {
		return tagDao.add(tag);
	}

	
	public int update(Tag tag) {
		return tagDao.update(tag);
	}

	
	public int delete(Integer id) {
		return tagDao.delete(id);
	}

}
