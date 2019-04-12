package com.chintoo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.chintoo.dao.ChintooPostRepository;
import com.chintoo.dao.MyCommentsInterface;
import com.chintoo.dao.MyLikeRepository;
import com.chintoo.dao.MyReactionRepository;
import com.chintoo.dao.MyUserRepository;
import com.chintoo.entity.ChintooPost;
import com.chintoo.entity.MyComments;
import com.chintoo.entity.MyLike;
import com.chintoo.entity.MyReaction;
import com.chintoo.entity.MyUser;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.Likes;
import com.restfb.types.Likes.LikeItem;
import com.restfb.types.MessageTag;
import com.restfb.types.Post;
import com.restfb.types.Reactions.ReactionItem;
import com.restfb.types.User;

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
	private MyCommentService myCommentService;

	@Autowired
	private MyReactionService myReactionService;


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
        
        char[] password = new char[6]; 
  
        for (int i = 0; i < 6; i++) 
        { 
            password[i] = 
              values.charAt(rndm_method.nextInt(values.length())); 
        } 
		
        return password.toString();
	}
	
	
}
