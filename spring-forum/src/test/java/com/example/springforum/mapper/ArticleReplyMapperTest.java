package com.example.springforum.mapper;

import com.example.springforum.model.DTO.ArticleReplyDTO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleReplyMapperTest {
    @Resource
    private ArticleReplyMapper articleReplyMapper;

    @Test
    void selectReplyByArticleId() {
        List<ArticleReplyDTO>  articleReplyDTOS = articleReplyMapper.selectReplyByArticleId(4L);
        for(ArticleReplyDTO articleReplyDTO:articleReplyDTOS){
            System.out.println(articleReplyDTO.toString());
        }
    }
}