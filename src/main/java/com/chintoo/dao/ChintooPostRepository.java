package com.chintoo.dao;

import org.springframework.data.repository.CrudRepository;

import com.chintoo.entity.ChintooPost;

public interface ChintooPostRepository extends CrudRepository<ChintooPost, String> {

}
