package com.lik.dao;

import java.util.List;
import java.util.Map;


import com.lik.entity.Comment;

public interface CommentDao {
	/**
	 * ��������
	 * @param Comment
	 * @return
	 */
	public List<Comment> find(Map<String,Object> map);
	/**
	 * ����һ����־����������
	 * @param Comment
	 * @return
	 */
	public List<Comment> findByLid(Integer id);
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
	public Comment findByCid(Integer fid);
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
