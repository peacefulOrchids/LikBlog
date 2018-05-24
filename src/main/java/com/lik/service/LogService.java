package com.lik.service;

import java.util.List;
import java.util.Map;

import com.lik.entity.Log;

public interface LogService {
	/**
	 * ������־
	 * @param Log
	 * @return
	 */
	public List<Log> find(Map<String,Object> map);
	/**
	 * ͨ��id������־
	 * @param Log
	 * @return
	 */
	public Log findById(Integer id);
	/**
	 * ��һ��
	 * @param id
	 * @return
	 */
	public Log findIdLast(Integer id);
	/**
	 * ��һ��
	 * @param id
	 * @return
	 */
	public Log findIdNext(Integer id);
	/**
	 * �õ���־����
	 */
	public Long countLogs(Map<String,Object> map);
	/**
	 * �����־
	 */
	public int add(Log logs);
	/**
	 * �޸���־
	 */
	public int update(Log logs);
	/**
	 * ɾ����־
	 */
	public int delete(Integer id);
	/**
	 * ��־�����/������+1
	 */
	public int addClickOrReply(Map<String,Object> map);
	/**
	 * ��־�����/������-1
	 */
	public int deleteClickOrReply(Map<String,Object> map);
}
