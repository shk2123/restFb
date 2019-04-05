package com.chintoo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chintoo.dao.ChintooPostRepository;
import com.chintoo.dao.MyCommentsInterface;
import com.chintoo.dao.MyLikeRepository;
import com.chintoo.dao.MyPostInterface;
import com.chintoo.dao.MyReactionRepository;
import com.chintoo.dao.MyUserRepository;
import com.chintoo.entity.ChintooPost;
import com.chintoo.entity.MyComments;
import com.chintoo.entity.MyLike;
import com.chintoo.entity.MyPost;
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

@Service
public class service {

	@Autowired
	private MyCommentsInterface myCommentsInterface;

	@Autowired
	private MyPostInterface myPostInterface;

	@Autowired
	private MyUserRepository myUserRepository;

	@Autowired
	private MyLikeRepository myLikeRepository;

	@Autowired
	private MyReactionRepository myReactionRepsoitory;

	@Autowired
	private ChintooPostRepository chintooPostRepository;

	private String generatedAcessToken;

	@Value("${fb.accessToken}")
	private String accessToken;
	@Value("${fb.postUniqueId}")
	private String postUniqueId;
	@Value("${fb.myAppId}")
	private String myAppId;
	@Value("${fb.myAppSecret}")
	private String myAppSecret;

	




	


	public String tokenGenerator() {
		String parsedAccessToken = accessToken;

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}

			AccessToken accessTokenGenerated = new DefaultFacebookClient(Version.LATEST)
					.obtainExtendedAccessToken(myAppId, myAppSecret, parsedAccessToken);
			System.out.println("My extended access token: " + accessTokenGenerated);
			String delims = "[=\\s+]";
			String[] parser = accessTokenGenerated.toString().split(delims);
			parsedAccessToken = parser[1];
			this.generatedAcessToken = parsedAccessToken;

		}
	}


}
