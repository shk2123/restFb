package com.chintoo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Service
public class service {


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
	
	public /*List<ChintooPost>*/ void getFeed(int numberOfPost) {
		
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<Post> postList = client.fetchConnection("me/feed", Post.class, Parameter.with("limit", 1));
		
		//List<ChintooPost> chintooPostList = new ArrayList<>();
		int limit = numberOfPost;
		for (List<Post> post : postList){
			for (Post myPost : post ){
				/*ChintooPost chintooPost = chintooPostRepository.findOne(myPost.getId());
				if (null == chintooPost){
					chintooPost.setId(myPost.getId());
					chintooPostList.add(chintooPostRepository.save(chintooPost));*/
				System.out.println("POST" + myPost.getId());
				limit--;
				
				if (limit == 0){
					
				}
				
				}
			}
		}
		
		//return chintooPostList;

	
	
	


}
