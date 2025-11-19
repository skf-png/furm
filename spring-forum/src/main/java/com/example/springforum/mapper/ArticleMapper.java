package com.example.springforum.mapper;

import com.example.springforum.model.Article;
import com.example.springforum.model.DTO.ArticleDTO;
import com.example.springforum.model.DTO.ArticleDetailDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper {
    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
    @Select("select u.id userId,\n" +
            "       u.avatarUrl,\n" +
            "       u.nickname,\n" +
            "       a.id,\n" +
            "       a.boardId,\n" +
            "       a.title,\n" +
            "       a.content,\n" +
            "       a.visitCount,\n" +
            "       a.replyCount,\n" +
            "       a.likeCount,\n" +
            "       a.state,\n" +
            "       a.createTime,\n" +
            "       a.updateTime\n" +
            "from t_article a,\n" +
            "     t_user u\n" +
            "where a.userId = u.id\n" +
            "  and a.deleteState = 0\n" +
            "order by a.createTime desc;")
    List<ArticleDTO> selectAllArticle();
    @Select("select u.id userId,\n" +
            "       u.avatarUrl,\n" +
            "       u.nickname,\n" +
            "       a.id,\n" +
            "       a.boardId,\n" +
            "       a.title,\n" +
            "       a.content,\n" +
            "       a.visitCount,\n" +
            "       a.replyCount,\n" +
            "       a.likeCount,\n" +
            "       a.state,\n" +
            "       a.createTime,\n" +
            "       a.updateTime\n" +
            "from t_article a,\n" +
            "     t_user u\n" +
            "where a.userId = u.id\n" +
            "  and a.deleteState = 0\n and boardId = #{BoardId} " +
            "order by a.createTime desc;")
    List<ArticleDTO> selectArticlesByBoardId(Long BoardId);

    @Select("select " +
            "       u.avatarUrl,\n" +
            "       u.nickname,\n" +
            "       u.id as userId, " +
            "       b.name,\n" +
            "       a.id,\n" +
            "       a.boardId,\n" +
            "       a.title,\n" +
            "       a.content,\n" +
            "       a.visitCount,\n" +
            "       a.replyCount,\n" +
            "       a.likeCount,\n" +
            "       a.state,\n" +
            "       a.createTime,\n" +
            "       a.updateTime\n" +
            "from t_article a,\n" +
            "     t_user u,\n" +
            "     t_board b\n" +
            "where a.userId = u.id\n" +
            "  and a.deleteState = 0\n" +
            "  and a.boardId = b.id\n" +
            "and a.id = #{Id} " +
            "order by a.createTime desc;")
    public ArticleDetailDTO selectDetailById(Long Id);
}