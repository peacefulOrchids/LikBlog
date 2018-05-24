package com.lik.service;

import java.util.List;
import java.util.Map;

import com.lik.entity.Tag;

public interface TagService {
	/**
	 * ���ұ�ǩ
	 * @param Tag
	 * @return
	 */
	public List<Tag> find(Map<String,Object> map);
	/**
	 * ͨ��id���ұ�ǩ
	 * @param Tag
	 * @return
	 */
	public Tag findById(Integer id);
	/**
	 * �õ���ǩ����
	 */
	public Long countTag(Map<String,Object> map);
	/**
	 * ��ӱ�ǩ
	 */
	public int add(Tag tag);
	/**
	 * �޸ı�ǩ
	 */
	public int update(Tag tag);
	/**
	 * ɾ����ǩ
	 */
	public int delete(Integer id);
}
