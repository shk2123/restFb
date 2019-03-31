package com.chintoo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MyPost {
    
	@Id
	private String id;
	@OneToMany
	private List<MyComments> comments;
	private long shares;
	@OneToMany(fetch=FetchType.EAGER)
	private List<MyLike> likes;
	
	

	public MyPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyPost(String id, List<MyComments> comments, long shares, List<MyLike> likes) {
		super();
		this.id = id;
		this.comments = comments;
		this.shares = shares;
		this.likes = likes;
	}

	public List<MyLike> getLikes() {
		return likes;
	}

	public void setLikes(List<MyLike> likes) {
		this.likes = likes;
	}

	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MyComments> getComments() {
		return comments;
	}

	public void setComments(List<MyComments> comments) {
		this.comments = comments;
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
		MyPost other = (MyPost) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
