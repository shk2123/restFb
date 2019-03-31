package com.chintoo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chintoo.entity.MyReaction;

public interface MyReactionRepository extends CrudRepository<MyReaction, String> {
	List<MyReaction> findByNameContainingIgnoreCase (String name);
}
