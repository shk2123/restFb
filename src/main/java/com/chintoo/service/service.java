package com.chintoo.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.GraphResponse;
import com.restfb.types.Likes;
import com.restfb.types.MessageTag;
import com.restfb.types.Post;
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
	
	@Value("${fb.accessToken}")
	private String accessToken;

	public MyPost getComments()

	{
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		User me = client.fetchObject("me", User.class);
		System.out.println(me.getName());

		Post post = client.fetchObject("1249356698415581_2696900666994503",Post.class,Parameter.with("fields", "comments{from,message,message_tags},likes,shares"));
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
			myComment.setName(fromUser.getName());

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


}
