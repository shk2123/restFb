package com.chintoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chintoo.entity.ChintooPost;
import com.chintoo.service.ChintooPostService;
import com.chintoo.service.MyCommentService;
import com.chintoo.service.MyReactionService;
import com.chintoo.service.service;

@RestController
public class controller {
	
	@Autowired
	public service Service;
	
	@Autowired
	public ChintooPostService chintooPostService;
	
	@Autowired
	public MyReactionService myReactionService;
	
	@Autowired
	public MyCommentService myCommentsService;

	@RequestMapping(method = RequestMethod.GET, value ="/posts")
	public Iterable<ChintooPost> getPosts()
	{
		return chintooPostService.getPostsFromDb();

	}
	
	@RequestMapping(method = RequestMethod.PUT, value ="/getAllComments/{postId}")
	public ChintooPost getAllComments(@PathVariable String postId)
	{
		return myCommentsService.getAllData(postId);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value ="/getAllLikes/{postId}")
	public ChintooPost getAllLikes(@PathVariable String postId)
	{
		return myReactionService.getAllReactions(postId);
		
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value ="/savePost/{postId},{postName}")
	public ChintooPost savePost(@PathVariable String postId, @PathVariable String postName)
	{
		return chintooPostService.savePost(postId, postName);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getPostById/{Id}")
	public ChintooPost getPostById(@PathVariable String Id)
	{
		return chintooPostService.getPostById(Id);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getLikesByUserAcrossPost/{myReactionName}")
	public List<ChintooPost> getReactsionByNameAcrossPosts(@PathVariable String myReactionName)
	{
		return myReactionService.getReactionByNameAcrossPost(myReactionName);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getCommentsByUserAcrossPost/{myReactionName}")
	public List<ChintooPost> getCommentsByNameAcrossPosts(@PathVariable String myReactionName)
	{
		return myCommentsService.getCommentsByNameAcrossPost(myReactionName);
		
	}
	
	
}
