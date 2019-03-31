package com.chintoo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class ChintooPost {

	@javax.persistence.Id
	private String Id;
	
	@OneToMany
	private List<MyReaction> myReaction;
	
	@OneToMany
	private List<MyComments> myComments;

	public ChintooPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChintooPost(String id, List<MyReaction> myReaction, List<MyComments> myComments) {
		super();
		Id = id;
		this.myReaction = myReaction;
		this.myComments = myComments;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public List<MyReaction> getMyReaction() {
		return myReaction;
	}

	public void setMyReaction(List<MyReaction> myReaction) {
		this.myReaction = myReaction;
	}

	public List<MyComments> getMyComments() {
		return myComments;
	}

	public void setMyComments(List<MyComments> myComments) {
		this.myComments = myComments;
	}


	
}
