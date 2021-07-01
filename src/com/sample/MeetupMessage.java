package com.sample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetupMessage {
	
	private int id;
	private String name;
	private Date date;
	private String comment;
	private List<MeetupMessage> replyList;
	
	public MeetupMessage(int id, String name, Date date, String comment) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.comment = comment;
		this.replyList = new ArrayList<>();
	}
	
	/**
	 * 返信用のメッセージを生成
	 * @param name
	 * @param date
	 * @param comment
	 */
	public MeetupMessage(String name, Date date, String comment) {
		super();
		this.id = -1;
		this.name = name;
		this.date = date;
		this.comment = comment;
		this.replyList = null;		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return df.format(date).toString();
	}

	public String getComment() {
		return comment;
	}

	public List<MeetupMessage> getReplyList() {
		return replyList;
	}

	public void addReply(MeetupMessage msg) {
		this.replyList.add(msg);
	}
}
