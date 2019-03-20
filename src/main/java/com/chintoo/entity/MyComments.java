package com.chintoo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class MyComments {

	//@Id
	private String id;
	MyUser from;
	private String message;
	private Date createdTime;
	private String likes;
	List<MyUser> messageTags;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MyUser getFrom() {
		return from;
	}
	public void setFrom(MyUser from) {
		this.from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date date) {
		this.createdTime = date;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public List<MyUser> getMessageTags() {
		return messageTags;
	}
	public void setMessageTags(List<MyUser> messageTags) {
		this.messageTags = messageTags;
	}
	public MyComments(String id, MyUser from, String message, Date createdTime, String likes,
			List<MyUser> messageTags) {
		super();
		this.id = id;
		this.from = from;
		this.message = message;
		this.createdTime = createdTime;
		this.likes = likes;
		this.messageTags = messageTags;
	}
	public MyComments() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MyComments [id=" + id + ", from=" + from + ", message=" + message + ", createdTime=" + createdTime
				+ ", likes=" + likes + ", messageTags=" + messageTags + "]";
	}
	
	
	
	
}
