package com.example.springforum.mapper;

import com.example.springforum.model.Board;

public interface BoardMapper {
    int insert(Board record);

    int insertSelective(Board record);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);
}