package com.example.springforum.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO {
    private Long userId;          // u.id
    private String avatarUrl; // u.avatarUrl
    private String nickname;  // u.nickname

    // === 来自 t_article 表的字段 ===
    private Long id;              // a.id
    private Long boardId;         // a.boardId
    private String title;         // a.title
    private String content;       // a.content
    private Integer visitCount;   // a.visitCount
    private Integer replyCount;   // a.replyCount
    private Integer likeCount;    // a.likeCount
    private Byte state;        // a.state
    private Date createTime;      // a.createTime
    private Date updateTime;      // a.updateTime
}
