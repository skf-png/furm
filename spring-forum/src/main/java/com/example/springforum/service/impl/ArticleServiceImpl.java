package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.ArticleMapper;
import com.example.springforum.model.Article;
import com.example.springforum.model.Board;
import com.example.springforum.model.DTO.ArticleDTO;
import com.example.springforum.model.DTO.ArticleDetailDTO;
import com.example.springforum.model.User;
import com.example.springforum.request.UpdateArticleRequest;
import com.example.springforum.service.ArticleService;
import com.example.springforum.service.BoardService;
import com.example.springforum.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.metadata.aggregated.AbstractPropertyCascadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleMapper.selectAllArticle();
    }

    @Override
    public List<ArticleDTO> getArticlesByBoardId(Long boardId) {
        if  (boardId == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        return articleMapper.selectArticlesByBoardId(boardId);
    }

    @Override
    public ArticleDetailDTO getArticleDetail(Long articleId, Boolean isAdd) {
        //判空
        if (articleId == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //获取文章细节
        ArticleDetailDTO articleDetailDTO = articleMapper.selectDetailById(articleId);
        //如果不存在
        if  (articleDetailDTO == null) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }
        //根据传参决定访问量是否增加
        if (isAdd) {
            //访问量+1
            Article article = new Article();
            article.setId(articleId);
            article.setVisitCount(articleDetailDTO.getVisitCount() + 1);
            //更新
            int row = articleMapper.updateByPrimaryKeySelective(article);
            if (row != 1) {
                throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
            }
        }

        //返回的访问量应该+1
        articleDetailDTO.setVisitCount(articleDetailDTO.getVisitCount() + 1);
        return articleDetailDTO;
    }

    @Override
    public void updateArticle(UpdateArticleRequest request) {
        if (request == null || request.getId() == null || request.getTitle() == null
        || request.getContent() == null) {
            log.warn(request.getContent() +  request.getTitle());
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        Article updateArticle = new Article();
        updateArticle.setId(request.getId());
        updateArticle.setTitle(request.getTitle());
        updateArticle.setContent(request.getContent());

        int row =  articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (row != 1) {
            log.warn(ResultCode.ERROR_SERVICES.toString());
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }
}
