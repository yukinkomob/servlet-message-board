package com.sample;

import java.util.Date;
import java.util.List;

public class Comment {
	private int id;
	private String text;
	private Date createdAt;
	private String userName;
	private List<Comment> replyList;

	public Comment(int id, String text, Date createdAt, String userName, List<Comment> replyList) {
		super();
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.userName = userName;
		this.replyList = replyList;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getUserName() {
		return userName;
	}

	public List<Comment> getReplyList() {
		return replyList;
	}
}