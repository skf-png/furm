package com.example.springforum.mapper;

import com.example.springforum.model.Message;

public interface MessageMapper {
    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}