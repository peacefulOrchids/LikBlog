package com.lik.entity;

import java.util.LinkedList;
import java.util.List;

public class Log {
	private int id;//编号
	private String title;//日志标题
	private String summary;//摘要
	private String content;//日志内容
	private String contentNoTag;//日志内容 没有标签
	private String createTime;//发布时间
	private Tag tag;//日志的标签
	private List<Comment> commentList;//评论
	private String keyWord;//关键字 空格隔开 用于全文检索
	private int clickHit;//点击量/浏览量
	private int replyHit;//评论量
	private Integer blogCount;//博客数量 非博客实例属性 主要是根据发布日期归档查询数量用到
	private String releaseDateStr;//发布日期的字符串 只取年和月
	private List<String> imageList = new LinkedList<String>();//博客里存在的图片
	
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
