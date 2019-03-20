package com.chintoo.entity;

import java.util.List;

public class MyPost {

	private String id;
	
	private List<MyComments> comments;

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
	
	
	
}
