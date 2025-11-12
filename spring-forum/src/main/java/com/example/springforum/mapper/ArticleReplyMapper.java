package com.example.springforum.mapper;

import com.example.springforum.model.ArticleReply;

public interface ArticleReplyMapper {
    int insert(ArticleReply record);

    int insertSelective(ArticleReply record);

    ArticleReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleReply record);

    int updateByPrimaryKey(ArticleReply record);
}