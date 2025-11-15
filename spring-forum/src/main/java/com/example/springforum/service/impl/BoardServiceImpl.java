package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.BoardMapper;
import com.example.springforum.model.Board;
import com.example.springforum.service.BoardService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
    @Resource
    private BoardMapper boardMapper;

    @Override
    public List<Board> getBoards(Integer number) {
        //判空
        if (number == null || number <= 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //返回
        return boardMapper.selectTopBoards(number);
    }

    @Override
    public void addOneBoardCount(Long id) {
        if  (id == null || id <= 0) {
//            log.info("")
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        Board board = boardMapper.selectByPrimaryKey(id);
        if (board == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        Board updateBoard = new Board();
        updateBoard.setId(id);
        updateBoard.setArticleCount(board.getArticleCount() + 1);
        int row = boardMapper.updateByPrimaryKeySelective(updateBoard);

        if (row != 1) {
            log.warn("更新不为1");
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }

    @Override
    public Board getBoardById(Long id) {
        if  (id == null || id <= 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        return boardMapper.selectByPrimaryKey(id);
    }
}
