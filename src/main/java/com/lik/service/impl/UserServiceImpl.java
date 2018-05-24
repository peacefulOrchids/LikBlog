package com.lik.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lik.dao.UserDao;
import com.lik.entity.User;
import com.lik.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	
	
	public User login(User user) {
		return userDao.login(user);
	}

	
	public int updatePwd(User user) {
		return userDao.updatePwd(user);
	}

	
	public List<User> find(Map<String,Object> map) {
		return userDao.find(map);
	}

	
	public User findById(Integer id) {
		return userDao.findById(id);
	}

	
	public Long countUser(Map<String,Object> map) {
		return userDao.countUser(map);
	}

	
	public int add(User user) {
		return userDao.add(user);
	}

	
	public int update(User user) {
		return userDao.update(user);
	}

	
	public int delete(Integer id) {
		return userDao.delete(id);
	}


	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

}
