package com.example.springforum.service;

import com.example.springforum.model.Board;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardService {
    List<Board> getBoards(Integer number);

    public void addOneBoardCount(Long id);

    public void subOneBoardCount(Long id);

    Board getBoardById(Long id);

}
