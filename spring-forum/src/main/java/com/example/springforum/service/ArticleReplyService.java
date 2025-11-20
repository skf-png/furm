package com.example.springforum.service;

import com.example.springforum.model.ArticleReply;
import com.example.springforum.model.DTO.ArticleReplyDTO;

import java.util.List;

public interface ArticleReplyService {
    void addReply(ArticleReply reply);

    List<ArticleReplyDTO> selectByArticleId(Long articleId);
}
