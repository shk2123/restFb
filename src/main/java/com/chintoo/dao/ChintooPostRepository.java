package com.chintoo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chintoo.entity.ChintooPost;
import com.chintoo.entity.MyReaction;

public interface ChintooPostRepository extends CrudRepository<ChintooPost, String> {
	
	//List<ChintooPost> findByMyReactionByName(String name);

	List<ChintooPost> findByMyReactionName(String myReactionName);
	List<ChintooPost> findByMyCommentsName(String myReactionName);

}
