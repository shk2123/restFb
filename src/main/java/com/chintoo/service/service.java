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

	public MyPost getComments()

	{
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		User me = client.fetchObject("me", User.class);
		System.out.println(me.getName());

		Post post = client.fetchObject(postUniqueId, Post.class,
				Parameter.with("fields", "comments{from,message,message_tags},likes,shares"));
		// System.out.println(post.getComments());
		Comments comments = post.getComments();
		List<Comment> commentList = comments.getData(); // juna list
		List<MyComments> myCommentList = new ArrayList<>(); // navin list

		Likes likes = post.getLikes();

		List<LikeItem> likeItems = likes.getData();

		List<MyLike> myLikes = new ArrayList<>();

		for (LikeItem likeItem : likeItems) {
			MyLike myLike = new MyLike();
			MyUser myUser = new MyUser();
			MyUser existingUser = myUserRepository.findOne(likeItem.getId());
			if (null != existingUser) {
				myLike.setUser(existingUser);
			} else {
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

			if (null != existingUser) {
				myComment.setFrom(existingUser);
			} else {
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

			myComment.setMessageTags((List<MyUser>) myUserRepository.save(myUserTags));
			myCommentList.add(myComment);

		}

		MyPost myPost = new MyPost();
		myPost.setId(post.getId());
		myPost.setComments((List<MyComments>) myCommentsInterface.save(myCommentList));
		myPost.setLikes(myLikes);
		myPost.setShares(post.getSharesCount());

		return myPostInterface.save(myPost);

	}

	public Iterable<MyPost> getPostsFromDb() {
		return myPostInterface.findAll();
	}

	public List<MyComments> getCommentsByUser(String name) {
		return myCommentsInterface.findByNameContainingIgnoreCase(name);
	}

	public ChintooPost getAllReactions(String postId) {

		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<ReactionItem> connectionReaction = client.fetchConnection(postId + "/reactions", ReactionItem.class);

		ChintooPost chPost = chintooPostRepository.findOne(postId);

		List<MyReaction> myReactionList = new ArrayList<>();

		for (List<ReactionItem> reactionItems : connectionReaction) {
			for (ReactionItem item : reactionItems) {

				MyReaction myReaction = myReactionRepsoitory.findOne(item.getId());
				System.out.println(myReaction + " Id = " + item.getId());
				if (null == myReaction){
					
					myReaction = new MyReaction();
					myReaction.setReactionId(item.getId());
					myReaction.setName(item.getName());
					myReaction.setType(item.getType());
					
					MyUser myUser = null;
					myUser = myUserRepository.findOne(item.getId());
					if (null != myUser) {
						myReaction.setUser(myUser);
					} else {
						myUser = new MyUser();
						myUser.setId(item.getId());
						myUser.setName(item.getName());
						myReaction.setUser(myUserRepository.save(myUser));
					}
					myReactionList.add(myReactionRepsoitory.save(myReaction));
				}
			}
		}	

		ChintooPost chintooPost = chintooPostRepository.findOne(postUniqueId);
		if (null == chintooPost) {
			chintooPost = new ChintooPost();
			chintooPost.setId(postUniqueId);
		}

		chintooPost.setMyReaction(myReactionList);

		if (null == chPost){
			chPost = new ChintooPost();
			chPost.setId(postId);
			chPost.setMyReaction(myReactionList);
		} else {
			chPost.getMyReaction().addAll(myReactionList);
		}

		return chintooPostRepository.save(chPost);

	}

	public ChintooPost getAllData() {
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<Comment> connectionComment = client.fetchConnection(postUniqueId + "/comments", Comment.class,
				Parameter.with("limit", 10));
		ChintooPost chPost = chintooPostRepository.findOne(postUniqueId);

		List<MyComments> myCommentList = new ArrayList<>(); // navin list

		for (List<Comment> commentPage : connectionComment) {

			for (Comment comment : commentPage) {

				MyComments myComment = myCommentsInterface.findOne(comment.getId());
				System.out.println(myComment + " Id = " + comment.getId());
				if (null == myComment) {
					myComment = new MyComments();
					myComment.setId(comment.getId());
					myComment.setCreatedTime(comment.getCreatedTime());
					CategorizedFacebookType fromUser = comment.getFrom();

					MyUser myUserFrom = myUserRepository.findOne(fromUser.getId());
					if (null == myUserFrom) {
						myUserFrom = new MyUser();
						myUserFrom.setId(fromUser.getId());
						myUserFrom.setName(fromUser.getName());
						myUserFrom = myUserRepository.save(myUserFrom);
					}
					myComment.setFrom(myUserFrom);
					myComment.setName(fromUser.getName());
					myComment.setMessage(comment.getMessage());
					List<MessageTag> msgTags = comment.getMessageTags();
					List<MyUser> myUserTags = new ArrayList<>();

					for (MessageTag messageTag : msgTags) {
						MyUser myUserTag = myUserRepository.findOne(messageTag.getId());
						if (null == myUserTag) {
							myUserTag = new MyUser();
							myUserTag.setId(messageTag.getId());
						}
						myUserTag.setName(messageTag.getName());
						myUserTags.add(myUserTag);
					}

					myComment.setMessageTags((List<MyUser>) myUserRepository.save(myUserTags));
					myCommentList.add(myCommentsInterface.save(myComment));
				}
			}
		}

		//myCommentList = (List<MyComments>) myCommentsInterface.save(myCommentList);

		if (null == chPost) {
			chPost = new ChintooPost();
			chPost.setId(postUniqueId);
			chPost.setMyComments(myCommentList);
		} else {
			chPost.getMyComments().addAll(myCommentList);
		}

		return chintooPostRepository.save(chPost);
	}


	public ChintooPost getPostById(String Id) {
		return chintooPostRepository.findOne(Id);
	}

	public List<MyReaction> getReactionByName(String name) {
		return myReactionRepsoitory.findByNameContainingIgnoreCase(name);
	}

	public List<ChintooPost> getReactionByNameAcrossPost(String myReactionName) {
		return chintooPostRepository.findByMyReactionName(myReactionName);
	}

	public String getReactsionByNameAcrossPosts(String myReactionName) {
		Iterable<ChintooPost> iterableChintoo = chintooPostRepository.findAll();
		String liked = null;
		for (ChintooPost chintooPost : iterableChintoo) {
			if (chintooPost.getId() != null) {
				liked = "true";
			} else {
				liked = "false";
			}

		}

		return liked;
	}

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

	public String getGeneratedAcessToken() {
		return generatedAcessToken;
	}

	public void setGeneratedAcessToken(String generatedAcessToken) {
		this.generatedAcessToken = generatedAcessToken;
	}

}
