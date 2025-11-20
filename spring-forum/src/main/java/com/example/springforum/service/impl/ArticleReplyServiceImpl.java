package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.ArticleReplyMapper;
import com.example.springforum.model.ArticleReply;
import com.example.springforum.model.DTO.ArticleReplyDTO;
import com.example.springforum.service.ArticleReplyService;
import com.example.springforum.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleReplyServiceImpl implements ArticleReplyService {
    @Resource
    ArticleReplyMapper articleReplyMapper;
    @Resource
    ArticleService articleService;

    @Transactional
    @Override
    public void addReply(ArticleReply reply) {
        if (reply == null || reply.getArticleId() == null || reply.getPostUserId() == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        int row =  articleReplyMapper.insertSelective(reply);

        if (row != 1) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
        articleService.addOneReplyCount(reply.getArticleId());
    }

    @Override
    public List<ArticleReplyDTO> selectByArticleId(Long articleId) {
        if (articleId == null ||  articleId <= 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        List<ArticleReplyDTO> result = articleReplyMapper.selectReplyByArticleId(articleId);

        return result;
    }
}
