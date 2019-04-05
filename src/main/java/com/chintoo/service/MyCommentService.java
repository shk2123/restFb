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
	private MyUserRepository myUserRepository;

	@Autowired
	private ChintooPostRepository chintooPostRepository;

	
	
	public ChintooPost getAllComments(String postId) {
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
