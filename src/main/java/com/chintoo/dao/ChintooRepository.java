package com.chintoo.dao;

import org.springframework.data.repository.CrudRepository;

import com.chintoo.entity.MyComments;

public interface ChintooRepository extends CrudRepository<MyComments, String> {
	
}
