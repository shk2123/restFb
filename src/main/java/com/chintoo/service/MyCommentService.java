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
import com.chintoo.entity.MyUser;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
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
import com.restfb.types.Likes.LikeItem;

@Service
public class MyCommentService {
	
	@Value("${fb.accessToken}")
	private String accessToken;
	
	@Value("${fb.postUniqueId}")
	private String postUniqueId;
	
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
	
	public ChintooPost getAllData(String postId) {
		//getAllReactions(postId);
		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<Comment> connectionComment = client.fetchConnection(postId + "/comments", Comment.class,
				Parameter.with("limit", 10),Parameter.with("fields", "message_tags,from,id,message"));
		ChintooPost chPost = chintooPostRepository.findOne(postId);

		List<MyComments> myCommentList = new ArrayList<>(); // navin list

		for (List<Comment> commentPage : connectionComment) {

			for (Comment comment : commentPage) {

				MyComments myComment = myCommentsInterface.findOne(comment.getId());

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
							myUserTag.setName(messageTag.getName());
							myUserTag = myUserRepository.save(myUserTag);
							myUserTags.add(myUserTag);
						}
					}
					myComment.setMessageTags(myUserTags);
					myCommentList.add(myCommentsInterface.save(myComment));
				}
			}
		}

		if (null == chPost) {
			chPost = new ChintooPost();
			chPost.setId(postId);
			chPost.setMyComments(myCommentList);
		} else {
			chPost.getMyComments().addAll(myCommentList);
		}

		return chintooPostRepository.save(chPost);
	}

	public List<ChintooPost> getCommentsByNameAcrossPost(String myReactionName) {
		return chintooPostRepository.findByMyCommentsName(myReactionName);
	}
	
	public List<MyComments> getCommentsByUser(String name) {
		return myCommentsInterface.findByNameContainingIgnoreCase(name);
	}

}
