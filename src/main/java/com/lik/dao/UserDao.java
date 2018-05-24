package com.lik.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lik.entity.PageBean;
import com.lik.entity.User;

public interface UserDao {
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);
	/**
	 * 通过用户名得到用户
	 * @param user
	 * @return
	 */
	public User getByUserName(String userName);
	/**
	 * 用户改密
	 * @param user
	 * @return
	 */
	public int updatePwd(User user);
	/**
	 * 查找用户
	 * @param user
	 * @return
	 */
	public List<User> find(Map<String,Object> map);
	/**
	 * 通过id查找用户
	 * @param user
	 * @return
	 */
	public User findById(Integer id);
	/**
	 * 得到用户数量
	 */
	public Long countUser(Map<String,Object> map);
	/**
	 * 添加用户
	 */
	public int add(User user);
	/**
	 * 修改用户
	 */
	public int update(User user);
	/**
	 * 删除用户
	 */
	public int delete(Integer id);
}
