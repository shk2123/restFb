package com.chintoo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class ChintooPost {

	@Id
	private String Id;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<MyReaction> myReaction;
	
	@OneToMany
	private List<MyComments> myComments;
	
	private String name;

	public ChintooPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChintooPost(String id, List<MyReaction> myReaction, List<MyComments> myComments, String name) {
		super();
		Id = id;
		this.myReaction = myReaction;
		this.myComments = myComments;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
