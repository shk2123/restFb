package com.chintoo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chintoo.dao.ChintooPostRepository;
import com.chintoo.dao.MyReactionRepository;
import com.chintoo.dao.MyUserRepository;
import com.chintoo.entity.ChintooPost;
import com.chintoo.entity.MyReaction;
import com.chintoo.entity.MyUser;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Reactions.ReactionItem;

@Service
public class MyReactionService {
	
	@Value("${fb.accessToken}")
	private String accessToken;
	
	@Autowired
	ChintooPostRepository chintooPostRepository;
	
	@Autowired
	MyReactionRepository myReactionRepository;
	
	@Autowired
	MyUserRepository myUserRepository;

	public ChintooPost getAllReactions(String postId) {

		FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_3_2);
		Connection<ReactionItem> connectionReaction = client.fetchConnection(postId + "/reactions", ReactionItem.class);

		ChintooPost chPost = chintooPostRepository.findOne(postId);

		List<MyReaction> myReactionList = new ArrayList<>();

		for (List<ReactionItem> reactionItems : connectionReaction) {
			for (ReactionItem item : reactionItems) {

				String reactionId = postId + "_" + item.getId();

				MyReaction myReaction = myReactionRepository.findOne(reactionId);
				System.out.println(myReaction + " Id = " + reactionId);
				if (null == myReaction){

					myReaction = new MyReaction();
					myReaction.setReactionId(reactionId);
					myReaction.setName(item.getName());
					myReaction.setType(item.getType());

					MyUser myUser = myUserRepository.findOne(item.getId());
					if (null == myUser) {
						myUser = new MyUser();
						myUser.setId(item.getId());
						myUser.setName(item.getName());
						myReaction.setUser(myUserRepository.save(myUser));
					} 
					myReactionList.add(myReactionRepository.save(myReaction));
				}
			}
		}	

		if (null == chPost){
			chPost = new ChintooPost();
			chPost.setId(postId);
			chPost.setMyReaction(myReactionList);
		} else {
			chPost.getMyReaction().addAll(myReactionList);
		}

		return chintooPostRepository.save(chPost);
	}
	
	public List<MyReaction> getReactionByName(String name) {
		return myReactionRepository.findByNameContainingIgnoreCase(name);
	}

	public List<ChintooPost> getReactionByNameAcrossPost(String myReactionName) {
		return chintooPostRepository.findByMyReactionName(myReactionName);
	}
}
