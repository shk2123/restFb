package com.chintoo.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chintoo.dao.MyCommentsInterface;
import com.chintoo.dao.MyLikeRepository;
import com.chintoo.dao.MyPostInterface;
import com.chintoo.dao.MyReactionRepository;
import com.chintoo.dao.MyUserRepository;
import com.chintoo.entity.MyComments;
import com.chintoo.entity.MyLike;
import com.chintoo.entity.MyPost;
import com.chintoo.entity.MyReaction;
import com.chintoo.entity.MyUser;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.GraphResponse;
import com.restfb.types.Likes;
import com.restfb.types.Likes.LikeItem;
import com.restfb.types.MessageTag;
import com.restfb.types.Post;
import com.restfb.types.Reactions.ReactionItem;
import com.restfb.types.User;
import com.restfb.types.send.IdMessageRecipient;


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
	
	@Value("${fb.accessToken}")
	private String accessToken;
	@Value("${fb.postUniqueId}")
	private String postUniqueId;

	public MyPost getComments()

	{		
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		User me = client.fetchObject("me", User.class);
		System.out.println(me.getName());

		Post post = client.fetchObject(postUniqueId,Post.class,Parameter.with("fields", "comments{from,message,message_tags},likes,shares"));
		//System.out.println(post.getComments());
		Comments comments = post.getComments();
		List<Comment> commentList = comments.getData();   //juna list
		List<MyComments> myCommentList = new ArrayList<>();  //navin list
		
		Likes likes = post.getLikes();
	
		List<LikeItem> likeItems = likes.getData();
		
		
		List<MyLike> myLikes = new ArrayList<>();
		

		for (LikeItem likeItem : likeItems) {
			MyLike myLike = new MyLike();
			MyUser myUser = new MyUser();
			MyUser existingUser = myUserRepository.findOne(likeItem.getId());
			if(null != existingUser){
				myLike.setUser(existingUser);
			}else {
				myUser.setId(likeItem.getId());
				myUser.setName(likeItem.getName());
				myLike.setUser(myUserRepository.save(myUser));
			
			}
			myLikes.add(myLike);
		}
		
		myLikes = (List<MyLike>) myLikeRepository.save(myLikes);
		
		for (Comment comment : commentList) {
			MyComments myComment = new MyComments();

			myComment.setId(comment.getId());
			myComment.setCreatedTime(comment.getCreatedTime());
			MyUser myUserFrom = new MyUser();
			MyUser existingUser = myUserRepository.findOne(comment.getId());
			
			if(null != existingUser){
				myComment.setFrom(existingUser);
			}
			else {
			CategorizedFacebookType fromUser = comment.getFrom();
			myUserFrom.setId(fromUser.getId());
			myUserFrom.setName(fromUser.getName());
			myComment.setFrom(myUserRepository.save(myUserFrom));
			myComment.setMessage(comment.getMessage());
			myComment.setName(fromUser.getName());
			}

			List<MessageTag> msgTags = comment.getMessageTags();
			List<MyUser> myUserTags = new ArrayList<>();

			for (MessageTag messageTag : msgTags) {
				MyUser myUserTag = new MyUser();

				myUserTag.setId(messageTag.getId());
				myUserTag.setName(messageTag.getName());
				myUserTags.add(myUserTag);
			}
			
			myComment.setMessageTags((List<MyUser>)myUserRepository.save(myUserTags));
			myCommentList.add(myComment);

		}

	
		MyPost myPost = new MyPost();
		myPost.setId(post.getId());
		myPost.setComments((List<MyComments>)myCommentsInterface.save(myCommentList));
		myPost.setLikes(myLikes);
		myPost.setShares(post.getSharesCount());
		
		return myPostInterface.save(myPost);
		
	}
	
	
	public Iterable<MyPost> getPostsFromDb(){
		return myPostInterface.findAll();
	}
	
	public List<MyComments> getCommentsByUser(String name) {
		return myCommentsInterface.findByNameContainingIgnoreCase(name);
	}
	
	public ResponseEntity<String> getCommentsString(){

		RestTemplate rest = new RestTemplate();

		return rest.getForEntity("https://graph.facebook.com/v3.2/1249356698415581_2696900666994503/?fields=shares,comments&access_token=EAAJTHrZCGdd4BANFKBObOIxhth5Lap3NupZCAeyJgjPAL96lc8jn1fVyu7Dznytie0dZCI8FE6rqbbqrMdUFD0eZBRwjtTmA9JS2ITgjy5znopDhwZCTL4XZC7C928em8YhzRz9Grb75gdZAGxFaJJkBqIH9oif7SwtPma73De2mFZBdzfFfg8snwfKV1avm1kAZD", String.class);

	}
	
	public String sendBulkMessages()
	{
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		GraphResponse publishMessageResponse = client.publish("me/feed", GraphResponse.class, Parameter.with("message","restfbtest"));
		return publishMessageResponse.getId();
	}
	
	public String getAppSecretProof()
	{
		String proof = new DefaultFacebookClient(Version.LATEST).obtainAppSecretProof(accessToken, "d2868eb969bfb7bcd6647a483d262306");
		return proof;
	}
	
	public List<MyLike> getAllLikes()
	{
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<LikeItem> connectionLikes = client.fetchConnection(postUniqueId + "/likes", LikeItem.class);
		//int personalLimit = 10000;
		
		List<MyLike> myLikeList = new ArrayList<>();
		for(List<LikeItem> likeItems : connectionLikes){
			for(LikeItem item : likeItems){
				System.out.println("Id:" + item.getId());
				MyLike myLike = new MyLike();
				MyUser myUser = null;
					myUser = myUserRepository.findOne(item.getId());
				if(null != myUser){
					myLike.setUser(myUser);
				}else {
					myUser = new MyUser();
					myUser.setId(item.getId());
					myUser.setName(item.getName());
					myLike.setUser(myUserRepository.save(myUser));
				}
				
				myLikeRepository.save(myLike);
				myLikeList.add(myLike);
			}
		}
		return myLikeList;
		
	}
	
	public List<MyReaction> getAllReactions()
	{
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<ReactionItem> connectionReaction = client.fetchConnection(postUniqueId + "/reactions", ReactionItem.class);
		//int personalLimit = 10000;
		
		List<MyReaction> myReactionList = new ArrayList<>();
		for(List<ReactionItem> reactionItems : connectionReaction){
			for(ReactionItem item : reactionItems){
				System.out.println("Id:" + item.getId());
				MyReaction myReaction = new MyReaction();
				MyUser myUser = null;
					myUser = myUserRepository.findOne(item.getId());
				if(null != myUser){
					myReaction.setUser(myUser);
				}else {
					myUser = new MyUser();
					myUser.setId(item.getId());
					myUser.setName(item.getName());
					myReaction.setUser(myUserRepository.save(myUser));
				}
				
				myReaction.setType(item.getType());
				myReactionList.add(myReaction);
				myReactionRepsoitory.save(myReaction);
			}
		}
		return myReactionList;
		
	}
	
	public List<MyComments> getAllData()

	{		
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Post post = client.fetchObject(postUniqueId,Post.class,Parameter.with("fields", "comments{from,message,message_tags},likes,shares"));
		Connection<Comment> connectionComment = client.fetchConnection(postUniqueId + "/comments", Comment.class, Parameter.with("limit", 10));
		int personalLimit = 10000;
		List<MyComments> myCommentList = new ArrayList<>();  //navin list
		for(List<Comment> commentPage : connectionComment ){
			for (Comment comment : commentPage) {
				MyComments myComment = new MyComments();

				myComment.setId(comment.getId());
				myComment.setCreatedTime(comment.getCreatedTime());
				MyUser myUserFrom = new MyUser();
				MyUser existingUser = myUserRepository.findOne(comment.getId());
				
				if(null != existingUser){
					myComment.setFrom(existingUser);
				}
				else {
				CategorizedFacebookType fromUser = comment.getFrom();
				myUserFrom.setId(fromUser.getId());
				myUserFrom.setName(fromUser.getName());
				myComment.setFrom(myUserRepository.save(myUserFrom));
				myComment.setMessage(comment.getMessage());
				myComment.setName(fromUser.getName());
				}

				List<MessageTag> msgTags = comment.getMessageTags();
				List<MyUser> myUserTags = new ArrayList<>();

				for (MessageTag messageTag : msgTags) {
					MyUser myUserTag = new MyUser();

					myUserTag.setId(messageTag.getId());
					myUserTag.setName(messageTag.getName());
					myUserTags.add(myUserTag);
				}
				
				myComment.setMessageTags((List<MyUser>)myUserRepository.save(myUserTags));
				myCommentList.add(myComment);
				personalLimit --;

				if (personalLimit == 0) {
				       
				    }

			}
		}
		
		MyPost myPost = new MyPost();
		myPost.setId(post.getId());
		myPost.setComments((List<MyComments>)myCommentsInterface.save(myCommentList));
		//myPost.setLikes(myLikes);
		myPost.setShares(post.getSharesCount());
		
		myPostInterface.save(myPost);
		return myCommentList;
		
	}
	
}
