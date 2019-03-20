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

import com.chintoo.entity.MyPost;
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
	
	/*@RequestMapping(method = RequestMethod.POST, value ="/saveComments")
	public MyPost saveComments()
	{
		
		Service.saveComments();
		return Service.getComments();

	}*/

	/*@RequestMapping(method = RequestMethod.GET, value ="/commentsString")
	public ResponseEntity<String> getCommentsString()
	{
		return Service.getCommentsString();

	}
	@RequestMapping(method = RequestMethod.GET, value ="/getFromDB")
	public Iterable<PostENtity> getCommentsString()
	{
		return Service.getFromDatabase();

	}*/
	
	
}
