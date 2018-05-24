package com.lik.service;

import java.util.List;
import java.util.Map;

import com.lik.entity.Link;

public interface LinkService {
	/**
	 * ������������
	 * @param Link
	 * @return
	 */
	public List<Link> find(Map<String,Object> map);
	/**
	 * ͨ��id������������
	 * @param Link
	 * @return
	 */
	public Link findById(Integer id);
	/**
	 * �õ�������������
	 */
	public Long countLink(Map<String,Object> map);
	/**
	 * �����������
	 */
	public int add(Link Link);
	/**
	 * �޸���������
	 */
	public int update(Link Link);
	/**
	 * ɾ����������
	 */
	public int delete(Integer id);
}
