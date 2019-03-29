package com.chintoo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MyLike {

	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private String likeId;
	
	@OneToOne
	MyUser user;

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public String getLikeId() {
		return likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	
}
