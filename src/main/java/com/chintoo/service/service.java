package com.chintoo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chintoo.dao.MyCommentsInterface;
import com.chintoo.dao.MyPostInterface;
import com.chintoo.dao.MyUserRepository;
import com.chintoo.entity.MyComments;
import com.chintoo.entity.MyPost;
import com.chintoo.entity.MyUser;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.Likes;
import com.restfb.types.MessageTag;
import com.restfb.types.Post;
import com.restfb.types.User;


@Service
public class service {

	@Autowired
	private MyCommentsInterface myCommentsInterface;
	
	@Autowired
	private MyPostInterface myPostInterface;
	
	@Autowired
	private MyUserRepository myUserRepository;

	public MyPost getComments()

	{
		String accessToken = "EAAJTHrZCGdd4BAGsbUc6MIZA7gcjcmxZBj3ejv2TKerIs8OyCVqlmg5Bm5fgAg6ItWc7p6pHAg1jZCpwbU4FeXIAVEaA9l4RFf6ZAWZBGdxEG8ogZC1U31ryzR3z4ogXuaHmCqWrmqK7Sw1Pmpv2nTaV7U4NBVhCMLllvmq1xaUriozZAIWN8wyec10WZCcJ0qt5uP9kX81JCKgZDZD";
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		User me = client.fetchObject("me", User.class);
		System.out.println(me.getName());

		Post post = client.fetchObject("1249356698415581_2696900666994503",Post.class,Parameter.with("fields", "comments{from,message,message_tags},likes"));
		//System.out.println(post.getComments());
		Comments comments = post.getComments();   //juna
		List<Comment> commentList = comments.getData();   //juna list
		List<MyComments> myCommentList = new ArrayList<>();  //navin list

		for (Comment comment : commentList) {
			MyComments myComment = new MyComments();

			myComment.setId(comment.getId());
			myComment.setCreatedTime(comment.getCreatedTime());
			MyUser myUserFrom = new MyUser();
			CategorizedFacebookType fromUser = comment.getFrom();
			myUserFrom.setId(fromUser.getId());
			myUserFrom.setName(fromUser.getName());
			myComment.setFrom(myUserRepository.save(myUserFrom));
			
			
			myComment.setMessage(comment.getMessage());

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
		myPost.setComments((List<MyComments>)myCommentsInterface.save(myCommentList));;

		
		return myPostInterface.save(myPost);

		
		
	}
	
	
	public Iterable<MyPost> getPostsFromDb(){
		return myPostInterface.findAll();
	}
	
	/*public MyPost saveComments()
	{
			String accessToken = "EAAJTHrZCGdd4BAGsbUc6MIZA7gcjcmxZBj3ejv2TKerIs8OyCVqlmg5Bm5fgAg6ItWc7p6pHAg1jZCpwbU4FeXIAVEaA9l4RFf6ZAWZBGdxEG8ogZC1U31ryzR3z4ogXuaHmCqWrmqK7Sw1Pmpv2nTaV7U4NBVhCMLllvmq1xaUriozZAIWN8wyec10WZCcJ0qt5uP9kX81JCKgZDZD";
			FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
			User me = client.fetchObject("me", User.class);
			System.out.println(me.getName());

			Post post = client.fetchObject("1249356698415581_2696900666994503",Post.class,Parameter.with("fields", "comments{from,message,message_tags},likes"));
			//System.out.println(post.getComments());
			Comments comments = post.getComments();   //juna
			List<Comment> commentList = comments.getData();   //juna list
			List<MyComments> myCommentList = new ArrayList<>();  //navin list

			for (Comment comment : commentList) {
				MyComments myComment = new MyComments();

				myComment.setId(comment.getId());
				myComment.setCreatedTime(comment.getCreatedTime());
				MyUser myUserFrom = new MyUser();
				CategorizedFacebookType fromUser = comment.getFrom();
				myUserFrom.setId(fromUser.getId());
				myUserFrom.setName(fromUser.getName());
				myComment.setFrom(myUserFrom);
				myComment.setMessage(comment.getMessage());

				List<MessageTag> msgTags = comment.getMessageTags();
				List<MyUser> myUserTags = new ArrayList<>();

				for (MessageTag messageTag : msgTags) {
					MyUser myUserTag = new MyUser();

					myUserTag.setId(messageTag.getId());
					myUserTag.setName(messageTag.getName());
					myUserTags.add(myUserTag);

				}

				myCommentList.add(myComment);

			}

			MyPost myPost = new MyPost();
			myPost.setId(post.getId());
			myPost.setComments(myCommentList);;


			myCommentsInterface.save(myCommentList);
			return myPost;
		
	}*/

	/*
	public Post getComments()
	{
		String accessToken = "EAAJTHrZCGdd4BAK7CeWaIHu4R3cvCsR5cwbNZBckYtglpXayeAp0ZArLTMyAjglNK9jrMzvcO7NVRmE3RZAtCOYtwxFDpiH7KST4gCu6JTMkCrsfv0emTxUQqLxr2Bd9V4wR0dmz29MShJA4QZAVIINCUmx8Ls90UXMOiRpjui7ZBz9t30EThjdRhSFmqy1aAZD";
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		MyUser me = client.fetchObject("me", MyUser.class);
		System.out.println(me.getName());
		Post post = client.fetchObject("1249356698415581_2696900666994503",Post.class,Parameter.with("fields", "comments{from,message,message_tags},likes"));
		System.out.println(post.getComments());

		PostENtity postentity = new PostENtity(post.getCommentsCount().toString(), post.getId());



		//repository.save(postentity);
		return post;



	}

	public Iterable<PostENtity> getFromDatabase()
	{
		return repository.findAll();
	}*/


	public ResponseEntity<String> getCommentsString(){

		RestTemplate rest = new RestTemplate();

		return rest.getForEntity("https://graph.facebook.com/v3.2/1249356698415581_2696900666994503/?fields=shares,comments&access_token=EAAJTHrZCGdd4BANFKBObOIxhth5Lap3NupZCAeyJgjPAL96lc8jn1fVyu7Dznytie0dZCI8FE6rqbbqrMdUFD0eZBRwjtTmA9JS2ITgjy5znopDhwZCTL4XZC7C928em8YhzRz9Grb75gdZAGxFaJJkBqIH9oif7SwtPma73De2mFZBdzfFfg8snwfKV1avm1kAZD", String.class);

	}



}
