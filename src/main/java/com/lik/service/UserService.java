package com.lik.service;

import java.util.List;
import java.util.Map;

import com.lik.entity.PageBean;
import com.lik.entity.User;
/**
 * �û�������
 * @author xiao
 *
 */
public interface UserService {
	/**
	 * ͨ���û����õ��û�
	 * @param user
	 * @return
	 */
	public User getByUserName(String userName);
	/**
	 * �û���¼
	 * @param user
	 * @return
	 */
	public User login(User user);
	/**
	 * �û�����
	 * @param user
	 * @return
	 */
	public int updatePwd(User user);
	/**
	 * �����û�
	 * @param user
	 * @return
	 */
	public List<User> find(Map<String,Object> map);
	/**
	 * ͨ��id�����û�
	 * @param user
	 * @return
	 */
	public User findById(Integer id);
	/**
	 * �õ��û�����
	 */
	public Long countUser(Map<String,Object> map);
	/**
	 * ����û�
	 */
	public int add(User user);
	/**
	 * �޸��û�
	 */
	public int update(User user);
	/**
	 * ɾ���û�
	 */
	public int delete(Integer id);
}
