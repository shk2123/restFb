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
import com.restfb.types.Post;

@RestController
public class controller {
	
	@Autowired
	public service Service;

/*	@RequestMapping(method = RequestMethod.GET, value ="/flights")
	public List<FlightSearch> getAllflights()
	{
		return Service.getAllflights();

	}

	@RequestMapping(method = RequestMethod.GET, value ="/flights/search")
	public List<FlightSearch> searchflights(@RequestParam("source")String source, @RequestParam("destination")String destination )
	{
		return Service.searchflights(source, destination);

	}
	
	@RequestMapping(method = RequestMethod.POST, value="/flights" )
	public void addFlight(@RequestBody FlightSearch flightSearch)
	{
		Service.addFlight(flightSearch);
	}

	@RequestMapping(method = RequestMethod.GET, value ="/flights/{flightId}")
	public FlightSearch getflightSearch(@PathVariable int flightId)
	{
		return Service.getflightSearch(flightId);
	}

	@RequestMapping(method = RequestMethod.PUT, value="/flights/{flightId}")
	public void updateFlights(@RequestBody FlightSearch flightSearch, @PathVariable int flightId)
	{
		Service.updateflightSearch(flightId, flightSearch);
	}

	@RequestMapping(method = RequestMethod.DELETE, value="/flights/{flightId}")
	public void deleteFlight(@PathVariable int flightId)
	{
		Service.deleteflightSearch(flightId);
	}
	*/
	@RequestMapping(method = RequestMethod.GET, value ="/comments")
	public MyPost getComments()
	{
		return Service.getComments();

	}

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
