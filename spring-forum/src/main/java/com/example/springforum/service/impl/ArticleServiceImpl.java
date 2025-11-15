package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.ArticleMapper;
import com.example.springforum.model.Article;
import com.example.springforum.model.Board;
import com.example.springforum.model.User;
import com.example.springforum.service.ArticleService;
import com.example.springforum.service.BoardService;
import com.example.springforum.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.metadata.aggregated.AbstractPropertyCascadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    BoardService boardService;
    @Resource
    UserService userService;
    @Resource
    ArticleMapper articleMapper;

    @Transactional
    @Override
    public void addArticle(Article article) {
        if (article == null || article.getContent() == null || article.getTitle() == null) {
            log.info(article.getContent() +  article.getTitle());
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        int row = articleMapper.insertSelective(article);
        if (row != 1) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
        //查找用户是否存在
        User user = userService.selectByUserId(article.getUserId());
        if (user == null) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        // 插入
        userService.addOneArticleCount(article.getUserId());
        //查找板块是否存在
        Board board = boardService.getBoardById(article.getBoardId());
        if (board == null) {
            throw new AppException(AppResult.failed("板块不存在"));
        }
        boardService.addOneBoardCount(article.getBoardId());

    }
}
