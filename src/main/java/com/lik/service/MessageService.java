package com.lik.service;

import java.util.List;
import java.util.Map;


import com.lik.entity.Message;

public interface MessageService {
	/**
	 * 查找日志
	 * @param Message
	 * @return
	 */
	public List<Message> find(Map<String,Object> map);
	/**
	 * 通过id查找日志
	 * @param Message
	 * @return
	 */
	public Message findById(Integer id);
	/**
	 * 得到日志数量
	 */
	public Long countMessage(Map<String,Object> map);
	/**
	 * 添加日志
	 */
	public int add(Message messages);
	/**
	 * 修改日志
	 */
	public int update(Message messages);
	/**
	 * 删除日志
	 */
	public int delete(Integer id);
}
