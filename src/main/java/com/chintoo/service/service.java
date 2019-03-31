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
	
	@Autowired
	private ChintooPostRepository chintooPostRepository;
	
	@Value("${fb.accessToken}")
	private String accessToken;
	@Value("${fb.postUniqueId}")
	private String postUniqueId;
	@Value("${fb.myAppId}")
	private String myAppId;
	@Value("${fb.myAppSecret}")
	private String myAppSecret;

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
		
	
	public ChintooPost getAllReactions(String postId)
	{
		String generatedAccessToken = tokenGenerator();
		FacebookClient client = new DefaultFacebookClient(generatedAccessToken, Version.VERSION_3_2);
		Connection<ReactionItem> connectionReaction = client.fetchConnection(postId + "/reactions", ReactionItem.class);
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
				
				myReaction.setName(item.getName());
				myReaction.setType(item.getType());
				myReactionList.add(myReaction);
				myReactionRepsoitory.save(myReaction);
			}
		}
		
		ChintooPost chintooPost = new ChintooPost();
		//chintooPost.setMyComments(getAllData());
		chintooPost.setId(postUniqueId);
		chintooPost.setMyReaction(myReactionList);
		chintooPostRepository.save(chintooPost);
		return chintooPost;
		
	}
	
	public List<MyComments> getAllData()

	{		
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<Comment> connectionComment = client.fetchConnection(postUniqueId + "/comments", Comment.class, Parameter.with("limit", 10));
		
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
				myCommentsInterface.save(myCommentList);
			}
		}
		
		return myCommentList;
	}
	
	public ChintooPost getPostById(String Id)
	{
		return chintooPostRepository.findOne(Id);
	}
	
	public List<MyReaction> getReactionByName(String name)
	{
		return myReactionRepsoitory.findByNameContainingIgnoreCase(name);
	}
	 
	public String tokenGenerator()
	{
			AccessToken accessTokenGenerated =
					  new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(myAppId,
							  myAppSecret, accessToken);
					System.out.println("My extended access token: " + accessTokenGenerated);
					String delims = "[=\\s+]";
					String[] parser = accessTokenGenerated.toString().split(delims);
					String parsedAccessToken = parser[1];
					return parsedAccessToken;
		
	}
	
}
