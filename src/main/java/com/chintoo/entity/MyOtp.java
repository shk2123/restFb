package com.chintoo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MyOtp {
	
	@Id
	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public MyOtp(String otp) {
		super();
		this.otp = otp;
	}

	public MyOtp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
