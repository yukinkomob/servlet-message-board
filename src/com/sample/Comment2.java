package com.sample;

import java.util.Date;
import java.util.List;

public class Comment2 {
	private int id;
	private int replyId;
	private int userId;
	private String comment;
	private Date createdAt;
	private String userName;
	private List<Comment2> replyList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<Comment2> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Comment2> replyList) {
		this.replyList = replyList;
	}
}
