package com.example.springforum.mapper;

import com.example.springforum.model.ArticleReply;
import com.example.springforum.model.DTO.ArticleReplyDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleReplyMapper {
    int insert(ArticleReply record);

    int insertSelective(ArticleReply record);

    ArticleReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleReply record);

    int updateByPrimaryKey(ArticleReply record);

    @Select("select u.id userId,\n" +
            "       u.avatarUrl,\n" +
            "       u.nickname,\n" +
            "       ar.*\n" +
            "from t_article_reply ar,\n" +
            "     t_user u\n" +
            "where ar.postUserId = u.id\n" +
            "  and ar.articleId = #{articleId}\n" +
            "  and ar.deleteState = 0\n" +
            "order by ar.createTime desc")
    List<ArticleReplyDTO> selectReplyByArticleId(Long articleId);
}