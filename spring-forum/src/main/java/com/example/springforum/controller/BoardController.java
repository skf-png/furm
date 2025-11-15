package com.example.springforum.controller;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.model.Board;
import com.example.springforum.service.BoardService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Resource
    private BoardService boardService;
    @Value("${forum.default.topBoardNum}")
    Integer defaultTopBoardNum;

    @GetMapping("/getBoards")
    public AppResult getBoards() {
        List<Board> boards = boardService.getBoards(defaultTopBoardNum);

        if (boards == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
        return AppResult.success(boards);
    }
}
