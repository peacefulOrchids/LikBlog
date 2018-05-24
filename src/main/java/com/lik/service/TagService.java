package com.lik.service;

import java.util.List;
import java.util.Map;

import com.lik.entity.Tag;

public interface TagService {
	/**
	 * 查找标签
	 * @param Tag
	 * @return
	 */
	public List<Tag> find(Map<String,Object> map);
	/**
	 * 通过id查找标签
	 * @param Tag
	 * @return
	 */
	public Tag findById(Integer id);
	/**
	 * 得到标签数量
	 */
	public Long countTag(Map<String,Object> map);
	/**
	 * 添加标签
	 */
	public int add(Tag tag);
	/**
	 * 修改标签
	 */
	public int update(Tag tag);
	/**
	 * 删除标签
	 */
	public int delete(Integer id);
}
