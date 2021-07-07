package com.sample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {
	private int id;
	private String text;
	private Date createdAt;
	private String userName;
	private List<Comment> replyList;

	public Comment() {
		super();
		this.id = -1;
		this.replyList = new ArrayList<>();
	}
	
	public Comment(int id, String text, Date createdAt, String userName, List<Comment> replyList) {
		super();
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.userName = userName;
		this.replyList = replyList;
	}
	
	public String getDateString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
		return df.format(createdAt);
	}
	
	public String getSample() {
		return "sample22";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public List<Comment> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Comment> replyList) {
		this.replyList = replyList;
	}
}