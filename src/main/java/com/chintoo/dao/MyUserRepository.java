package com.chintoo.dao;

import org.springframework.data.repository.CrudRepository;

import com.chintoo.entity.MyUser;

public interface MyUserRepository extends CrudRepository<MyUser, String> {

}
