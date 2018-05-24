package com.lik.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lik.dao.CommentDao;
import com.lik.dao.LogDao;
import com.lik.entity.Comment;
import com.lik.service.CommentService;

@Service("commentService")
public class CommentServiceImpl  implements CommentService{

	@Resource
	private CommentDao commentDao;

	@Resource
	private LogDao logDao;
	public Comment findById(Integer id) {
		return commentDao.findById(id);
	}

	public Comment findByCid(Integer id) {
		return commentDao.findByCid(id);
	}

	public Long countComments(Map<String, Object> map) {
		return commentDao.countComments(map);
	}

	public int add(Comment comment) {
		return commentDao.add(comment);
		
	}

	public int update(Comment comment) {
		return commentDao.update(comment);
	}

	public int delete(Integer id) {
		return commentDao.delete(id);
	}

	public List<Comment> find(Map<String, Object> map) {
		return commentDao.find(map);
	}

}
