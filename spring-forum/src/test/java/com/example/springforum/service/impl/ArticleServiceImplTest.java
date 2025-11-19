package com.example.springforum.service.impl;

import com.example.springforum.mapper.ArticleMapper;
import com.example.springforum.model.DTO.ArticleDetailDTO;
import com.example.springforum.request.UpdateArticleRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceImplTest {
    @Resource
    private ArticleServiceImpl articleServiceImpl;
    @Resource
    private ArticleMapper articleMapper;
    @Test
    void getArticleDetail() {
        ArticleDetailDTO articleDetail = articleMapper.selectDetailById(1l);
        articleDetail = articleServiceImpl.getArticleDetail(1l);
        System.out.println(articleDetail.toString());
    }

    @Test
    void updateArticle() {
        UpdateArticleRequest request = new UpdateArticleRequest();
        request.setId(1l);
        request.setTitle("title");
        request.setContent("content");
        articleServiceImpl.updateArticle(request);
    }
}
