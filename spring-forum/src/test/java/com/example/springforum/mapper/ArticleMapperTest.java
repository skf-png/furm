package com.example.springforum.mapper;

import com.example.springforum.model.DTO.ArticleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleMapperTest {
    @Autowired
    private ArticleMapper articleMapper;
    @Test
    void selectAllArticle() {
        List<ArticleDTO> articles = articleMapper.selectArticlesByBoardId(1L);
        for (ArticleDTO articleDTO : articles) {
            System.out.println(articleDTO);
        }

    }
}
