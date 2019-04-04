package com.chintoo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chintoo.entity.MyComments;


public interface MyCommentsInterface extends CrudRepository<MyComments, String> {

	List<MyComments> findByNameContainingIgnoreCase (String name);
	
}
