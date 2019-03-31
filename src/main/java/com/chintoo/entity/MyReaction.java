package com.chintoo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MyReaction {
	
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private String ReactionId;
	
	@OneToOne
	MyUser user;
	
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MyReaction(String reactionId, MyUser user, String type) {
		super();
		ReactionId = reactionId;
		this.user = user;
		this.type = type;
	}

	public MyReaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
