package com.chintoo.dao;

import org.springframework.data.repository.CrudRepository;

import com.chintoo.entity.MyNumber;

public interface MyNumberRepository extends CrudRepository<MyNumber, String>{

}
