package com.chintoo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MyNumber {
	
	@Id
	private String mynumber;

	public String getMynumber() {
		return mynumber;
	}

	public void setMynumber(String mynumber) {
		this.mynumber = mynumber;
	}

	public MyNumber(String mynumber) {
		super();
		this.mynumber = mynumber;
	}

	public MyNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

}
