package com.chintoo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chintoo.entity.MyComments;


public interface MyCommentsInterface extends CrudRepository<MyComments, String> {

}
