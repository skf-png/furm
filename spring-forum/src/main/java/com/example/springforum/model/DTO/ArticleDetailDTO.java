package com.example.springforum.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailDTO {
    private String avatarUrl; // u.avatarUrl
    private String nickname;  // u.nickname
    private String name;
    private Long userId;

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
    private Boolean isOwn = false;
}
