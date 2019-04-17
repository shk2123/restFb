package com.chintoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chintoo.entity.ChintooPost;
import com.chintoo.service.ChintooPostService;
import com.chintoo.service.MyCommentService;
import com.chintoo.service.MyReactionService;
import com.chintoo.service.service;


@RequestMapping("/chintoo")
@RestController


public class Controller {
	
	@Autowired
	public service Service;
	
	@Autowired
	public ChintooPostService chintooPostService;
	
	@Autowired
	public MyReactionService myReactionService;
	
	@Autowired
	public MyCommentService myCommentsService;
	
	@Value("${fb.accessToken}")
	private String accessToken;


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(method = RequestMethod.GET, value ="/posts")
	public Iterable<ChintooPost> getPosts()
	{
		return chintooPostService.getPostsFromDb();

	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/savePost/{postId},{postName}")
	public ChintooPost savePost(@PathVariable String postId, @PathVariable String postName)
	{
		return chintooPostService.savePost(postId, postName);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getPostById/{Id}")
	public ChintooPost getPostById(@PathVariable String Id)
	{
		return chintooPostService.getPostById(Id);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value ="/deletePostById/{Id}")
	public String deletePostById(@PathVariable String Id)
	{
		chintooPostService.deletePostById(Id);
		String deleted = "deleted post" + Id;
		return deleted;
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/updatePostById/{Id}")
	public ChintooPost updatePost(@RequestBody ChintooPost chintooPost, @PathVariable String Id)
	{
		return chintooPostService.updatePost(Id, chintooPost);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	@RequestMapping(method = RequestMethod.GET, value ="/getLikesByUserAcrossPost/{myReactionName}")
	public List<ChintooPost> getReactsionByNameAcrossPosts(@PathVariable String myReactionName)
	{
		return myReactionService.getReactionByNameAcrossPost(myReactionName);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/statusForOtp/{myReactionName}")
	public Boolean getStatusForOtp(@PathVariable String myReactionName)
	{
		return myReactionService.statusForOtp(myReactionName);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getCommentsByUserAcrossPost/{myReactionName}")
	public List<ChintooPost> getCommentsByNameAcrossPosts(@PathVariable String myReactionName)
	{
		return myCommentsService.getCommentsByNameAcrossPost(myReactionName);
		
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(method = RequestMethod.POST, value ="/getAllComments/{postId}")
	public ChintooPost getAllComments(@PathVariable String postId)
	{
		return myCommentsService.getAllComments(postId);
		
	}
	
    @RequestMapping(method = RequestMethod.POST, value ="/getAllLikes/{postId}")
	public ChintooPost getAllLikes(@PathVariable String postId)
	{
		return myReactionService.getAllReactions(postId, accessToken);
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value ="/automated")
	public void automated()
	{
		Service.getPostIds();
		
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(method = RequestMethod.GET, value ="/getOtp")
	public String generateOtp()
	{
		return Service.generateOtp();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getOtpStatus/{myOtp}")
	public String findOtp(@PathVariable String myOtp)
	{
		return Service.findOtp(myOtp);
		
	}
	
	

	
}
