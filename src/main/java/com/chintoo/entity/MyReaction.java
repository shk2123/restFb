package com.chintoo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MyReaction {
	
	@Id
	private String ReactionId;
	
	@OneToOne
	MyUser user;
	
	private String name;

	private String type;

	public MyReaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyReaction(String reactionId, MyUser user, String name, String type) {
		super();
		ReactionId = reactionId;
		this.user = user;
		this.name = name;
		this.type = type;
	}

	public String getReactionId() {
		return ReactionId;
	}

	public void setReactionId(String reactionId) {
		ReactionId = reactionId;
	}

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
