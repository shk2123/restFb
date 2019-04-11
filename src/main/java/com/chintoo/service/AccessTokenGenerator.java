package com.chintoo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;


@Component
public class AccessTokenGenerator {
	
	 private static final Logger log = LoggerFactory.getLogger(AccessTokenGenerator.class);

	    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	//@Scheduled(fixedRate = 5000)
	public void scheduledTaskTest() {
		
		log.info("The time is now {}", dateFormat.format(new Date()));

	}
	
	}
