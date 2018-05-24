package com.lik.service;

import java.util.List;
import java.util.Map;


import com.lik.entity.Comment;

public interface CommentService {
	/**
	 * ��������
	 * @param Comment
	 * @return
	 */
	public List<Comment> find(Map<String,Object> map);
	/**
	 * ͨ��id��������
	 * @param Comment
	 * @return
	 */
	public Comment findById(Integer id);
	/**
	 * �ҵ���id��������
	 * @param Comment
	 * @return
	 */
	public Comment findByCid(Integer id);
	/**
	 * �õ���������
	 */
	public Long countComments(Map<String,Object> map);
	/**
	 * �������
	 */
	public int add(Comment comment);
	/**
	 * �޸�����
	 */
	public int update(Comment comment);
	/**
	 * ɾ������
	 */
	public int delete(Integer id);
}
