package com.example.springforum.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private Long id;

    private Long postUserId;

    private Long receiveUserId;

    private String content;

    private Byte state;

    private Byte deleteState;

    private Date createTime;

    private Date updateTime;

    private String avatarUrl;

    private String nickname;
}
