package com.lik.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lik.dao.MessageDao;
import com.lik.entity.Message;
import com.lik.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageDao messageDao;
	
	public List<Message> find(Map<String, Object> map) {
		return messageDao.find(map);
	}

	
	public Message findById(Integer id) {
		return messageDao.findById(id);
	}

	
	public Long countMessage(Map<String, Object> map) {
		return messageDao.countMessages(map);
	}

	
	public int add(Message messages) {
		return messageDao.add(messages);
	}

	
	public int update(Message messages) {
		return messageDao.update(messages);
	}

	
	public int delete(Integer id) {
		return messageDao.delete(id);
	}

}
