package com.example.springBoot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import com.example.springBoot.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

	public User getUser(User realUser);

}
