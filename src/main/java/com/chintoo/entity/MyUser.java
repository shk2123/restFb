package com.chintoo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class MyUser {
	
	//@Id
	private String id;
	private String name;
	private String metadata;
	private String type;
	private String category;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public MyUser(String id, String name, String metadata, String type, String category) {
		super();
		this.id = id;
		this.name = name;
		this.metadata = metadata;
		this.type = type;
		this.category = category;
	}
	public MyUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", metadata=" + metadata + ", type=" + type + ", category="
				+ category + "]";
	}
	
	
	
	
	
	

}
