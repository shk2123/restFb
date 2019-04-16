package com.chintoo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.chintoo.dao.ChintooPostRepository;
import com.chintoo.dao.MyOtpRepository;
import com.chintoo.entity.ChintooPost;
import com.chintoo.entity.MyOtp;


@Component
@Service
public class service {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private static final Logger log = LoggerFactory.getLogger(service.class);

	private String generatedAcessToken;

	@Value("${fb.accessToken}")
	private String accessToken;
	@Value("${fb.postUniqueId}")
	private String postUniqueId;
	@Value("${fb.myAppId}")
	private String myAppId;
	@Value("${fb.myAppSecret}")
	private String myAppSecret;

	@Autowired
	private ChintooPostRepository chintooPostRepository;

	@Autowired
	private MyReactionService myReactionService;

	@Autowired
	private MyOtpRepository myOtpRepository;


	@Scheduled(fixedRate= 60 * 1000 * 15)
	public void getPostIds() {

		Iterable<ChintooPost> chintooPostList = chintooPostRepository.findAll();

		for (ChintooPost chintooPosts : chintooPostList)
		{
			String myPostId =chintooPosts.getId();

			System.out.println("-------------------------------------------------------------------------------------------------");
			System.out.println("fetching reactions for postId" + myPostId);
			myReactionService.getAllReactions(myPostId, accessToken);
			System.out.println("------------------------------------------------------------------------------------------------..time is" + dateFormat.format(new Date()));
			System.out.println("fetched reactions for postId" + myPostId);

		}
	}

	public String generateOtp() {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		String Small_chars = "abcdefghijklmnopqrstuvwxyz"; 
		String numbers = "0123456789"; 
		String symbols = "!@#$%^&*_=+-/.?<>)";

		String values = Capital_chars + Small_chars + 
				numbers + symbols;

		Random rndm_method = new Random(); 

		char[] password = new char[7]; 

		for (int i = 0; i < 6; i++) 
		{ 
			password[i] = 
					values.charAt(rndm_method.nextInt(values.length())); 
		}

		MyOtp myOtp = myOtpRepository.findOne(password.toString());
		if(null == myOtp){
			myOtp = new MyOtp();
			myOtp.setOtp(password.toString());
			myOtpRepository.save(myOtp);
			return password.toString();
		}

		else{
			String newOtp = generateOtp();
			return newOtp;
		}

	}


	public String findOtp(String myOtp) {

		MyOtp otp = myOtpRepository.findOne(myOtp);
		if(null != otp){
			String presentOtp = "OTP exists";
			return presentOtp;
		}
		else{
			String noOtp = "OTP doesnt exists";
			return noOtp;
		}
	}


}
