package com.lik.service;

import java.util.List;
import java.util.Map;

import com.lik.entity.Log;

public interface LogService {
	/**
	 * 查找日志
	 * @param Log
	 * @return
	 */
	public List<Log> find(Map<String,Object> map);
	/**
	 * 通过id查找日志
	 * @param Log
	 * @return
	 */
	public Log findById(Integer id);
	/**
	 * 上一条
	 * @param id
	 * @return
	 */
	public Log findIdLast(Integer id);
	/**
	 * 下一条
	 * @param id
	 * @return
	 */
	public Log findIdNext(Integer id);
	/**
	 * 得到日志数量
	 */
	public Long countLogs(Map<String,Object> map);
	/**
	 * 添加日志
	 */
	public int add(Log logs);
	/**
	 * 修改日志
	 */
	public int update(Log logs);
	/**
	 * 删除日志
	 */
	public int delete(Integer id);
	/**
	 * 日志点击量/评论量+1
	 */
	public int addClickOrReply(Map<String,Object> map);
	/**
	 * 日志点击量/评论量-1
	 */
	public int deleteClickOrReply(Map<String,Object> map);
}
