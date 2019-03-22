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
	private String name;
	
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

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MyUser> getMessageTags() {
		return messageTags;
	}

	public void setMessageTags(List<MyUser> messageTags) {
		this.messageTags = messageTags;
	}

	
	public MyComments(String id, MyUser from, String message, Date createdTime, String likes, String name,
			String shares, List<MyUser> messageTags) {
		super();
		this.id = id;
		this.from = from;
		this.message = message;
		this.createdTime = createdTime;
		this.likes = likes;
		this.name = name;
		this.messageTags = messageTags;
	}

	public MyComments() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MyComments [id=" + id + ", from=" + from + ", message=" + message + ", createdTime=" + createdTime
				+ ", likes=" + likes + ", name=" + name + ", messageTags=" + messageTags + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdTime == null) ? 0 : createdTime.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((messageTags == null) ? 0 : messageTags.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (createdTime == null) {
			if (other.createdTime != null)
				return false;
		} else if (!createdTime.equals(other.createdTime))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (likes == null) {
			if (other.likes != null)
				return false;
		} else if (!likes.equals(other.likes))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (messageTags == null) {
			if (other.messageTags != null)
				return false;
		} else if (!messageTags.equals(other.messageTags))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
