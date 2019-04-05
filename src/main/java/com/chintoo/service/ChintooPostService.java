package com.chintoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chintoo.dao.ChintooPostRepository;
import com.chintoo.entity.ChintooPost;

@Service
public class ChintooPostService {
	
	@Autowired
	ChintooPostRepository chintooPostRepository;
	
	public Iterable<ChintooPost> getPostsFromDb() {
		return chintooPostRepository.findAll();
	}
	
	public ChintooPost savePost(String postId, String postName) {
		ChintooPost post = chintooPostRepository.findOne(postId);
		if(null == post){
			post = new ChintooPost();
			post.setId(postId);
			post.setName(postName);	
		}
		return chintooPostRepository.save(post);
	}
	
	public ChintooPost getPostById(String Id) {
		return chintooPostRepository.findOne(Id);
	}
	
	public void deletePostById(String Id) {
		chintooPostRepository.delete(Id);
	}
	
	public ChintooPost updatePost(String Id, ChintooPost chintooPost){
		return chintooPostRepository.save(chintooPost);
	}



}
