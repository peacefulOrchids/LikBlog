package com.lik.service;

import java.util.List;
import java.util.Map;

import com.lik.entity.Link;

public interface LinkService {
	/**
	 * 查找友情链接
	 * @param Link
	 * @return
	 */
	public List<Link> find(Map<String,Object> map);
	/**
	 * 通过id查找友情链接
	 * @param Link
	 * @return
	 */
	public Link findById(Integer id);
	/**
	 * 得到友情链接数量
	 */
	public Long countLink(Map<String,Object> map);
	/**
	 * 添加友情链接
	 */
	public int add(Link Link);
	/**
	 * 修改友情链接
	 */
	public int update(Link Link);
	/**
	 * 删除友情链接
	 */
	public int delete(Integer id);
}
