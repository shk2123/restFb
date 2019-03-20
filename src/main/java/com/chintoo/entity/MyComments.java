package com.chintoo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class MyComments {

	@Id
	private String id;
	
	@OneToOne
	MyUser from;
	private String message;
	private Date createdTime;
	private String likes;
	
	@OneToMany
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyComments other = (MyComments) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
