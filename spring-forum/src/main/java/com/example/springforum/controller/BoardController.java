package com.example.springforum.controller;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.model.Board;
import com.example.springforum.service.BoardService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/getBoardById")
    public AppResult getBoardById(@RequestParam @NotNull Long id) {
        Board board = boardService.getBoardById(id);
        if (board == null ||  board.getDeleteState() == 1) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }
        return AppResult.success(board);
    }

}
