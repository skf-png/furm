package com.example.springforum.service;

import com.example.springforum.model.Article;
import com.example.springforum.model.DTO.ArticleDTO;
import com.example.springforum.model.DTO.ArticleDetailDTO;
import com.example.springforum.request.UpdateArticleRequest;

import java.util.List;

public interface ArticleService {
    public void addArticle(Article article);

    public List<ArticleDTO> getAllArticles();

    public List<ArticleDTO> getArticlesByBoardId(Long boardId);

    public ArticleDetailDTO getArticleDetail(Long articleId, Boolean isAdd);

    public void updateArticle(UpdateArticleRequest request);

    void addLikeCount(Long id);

    void deleteArticle(Long id);

    void addOneReplyCount(Long id);

    List<ArticleDetailDTO> getArticlesByUserId(Long userId);
}
