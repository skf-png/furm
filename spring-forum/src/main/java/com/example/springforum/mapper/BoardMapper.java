package com.example.springforum.mapper;

import com.example.springforum.model.Board;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoardMapper {
    int insert(Board record);

    int insertSelective(Board record);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);
    @Select("select * from t_board limit 0,#{number}")
    List<Board> selectTopBoards(Integer number);
}