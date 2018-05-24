package com.lik.dao;

import java.util.List;
import java.util.Map;


import com.lik.entity.Message;

public interface MessageDao {
	/**
	 * ������־
	 * @param Message
	 * @return
	 */
	public List<Message> find(Map<String,Object> map);
	/**
	 * ͨ��id������־
	 * @param Message
	 * @return
	 */
	public Message findById(Integer id);
	/**
	 * �õ���־����
	 */
	public Long countMessages(Map<String,Object> map);
	/**
	 * �����־
	 */
	public int add(Message messages);
	/**
	 * �޸���־
	 */
	public int update(Message messages);
	/**
	 * ɾ����־
	 */
	public int delete(Integer id);
}
