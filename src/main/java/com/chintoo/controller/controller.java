package com.chintoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chintoo.entity.MyComments;
import com.chintoo.entity.MyLike;
import com.chintoo.entity.MyPost;
import com.chintoo.entity.MyReaction;
import com.chintoo.service.service;

@RestController
public class controller {
	
	@Autowired
	public service Service;

	@RequestMapping(method = RequestMethod.GET, value ="/getComments")
	public MyPost getComments()
	{
		return Service.getComments();

	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/posts")
	public Iterable<MyPost> getPosts()
	{
		return Service.getPostsFromDb();

	}
	
	
	@RequestMapping(method = RequestMethod.GET, value ="/getCommentsByUser/{name}")
	public List<MyComments> getCommentsByUser(@PathVariable String name)
	{
		return Service.getCommentsByUser(name);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/sendBulkMessage")
	public String sendBulkMessages()
	{
		return Service.sendBulkMessages();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getAccTokenProof")
	public String getAccTokenProof()
	{
		return Service.getAppSecretProof();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getAllData")
	public List<MyComments> getAllData()
	{
		return Service.getAllData();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getAllLikes")
	public List<MyReaction> getAllLikes()
	{
		return Service.getAllReactions();
		
	}
	
	
	
}
