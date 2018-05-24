package com.lik.entity;

import java.util.LinkedList;
import java.util.List;

public class Log {
	private int id;//���
	private String title;//��־����
	private String summary;//ժҪ
	private String content;//��־����
	private String contentNoTag;//��־���� û�б�ǩ
	private String createTime;//����ʱ��
	private Tag tag;//��־�ı�ǩ
	private List<Comment> commentList;//����
	private String keyWord;//�ؼ��� �ո���� ����ȫ�ļ���
	private int clickHit;//�����/�����
	private int replyHit;//������
	private Integer blogCount;//�������� �ǲ���ʵ������ ��Ҫ�Ǹ��ݷ������ڹ鵵��ѯ�����õ�
	private String releaseDateStr;//�������ڵ��ַ��� ֻȡ�����
	private List<String> imageList = new LinkedList<String>();//��������ڵ�ͼƬ
	
	public String getContentNoTag() {
		return contentNoTag;
	}
	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public int getReplyHit() {
		return replyHit;
	}
	public void setReplyHit(int replyHit) {
		this.replyHit = replyHit;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getClickHit() {
		return clickHit;
	}
	public void setClickHit(int clickHit) {
		this.clickHit = clickHit;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
