package com.chintoo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chintoo.entity.MyPost;


public interface MyPostInterface extends CrudRepository<MyPost, String> {

}
