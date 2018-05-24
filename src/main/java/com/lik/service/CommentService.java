package com.lik.service;

import java.util.List;
import java.util.Map;


import com.lik.entity.Comment;

public interface CommentService {
	/**
	 * 查找评论
	 * @param Comment
	 * @return
	 */
	public List<Comment> find(Map<String,Object> map);
	/**
	 * 通过id查找评论
	 * @param Comment
	 * @return
	 */
	public Comment findById(Integer id);
	/**
	 * 找到该id的子评论
	 * @param Comment
	 * @return
	 */
	public Comment findByCid(Integer id);
	/**
	 * 得到评论数量
	 */
	public Long countComments(Map<String,Object> map);
	/**
	 * 添加评论
	 */
	public int add(Comment comment);
	/**
	 * 修改评论
	 */
	public int update(Comment comment);
	/**
	 * 删除评论
	 */
	public int delete(Integer id);
}
