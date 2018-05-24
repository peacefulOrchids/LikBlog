package com.lik.dao;

import java.util.List;
import java.util.Map;


import com.lik.entity.Comment;

public interface CommentDao {
	/**
	 * 查找评论
	 * @param Comment
	 * @return
	 */
	public List<Comment> find(Map<String,Object> map);
	/**
	 * 查找一个日志的所有评论
	 * @param Comment
	 * @return
	 */
	public List<Comment> findByLid(Integer id);
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
	public Comment findByCid(Integer fid);
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
