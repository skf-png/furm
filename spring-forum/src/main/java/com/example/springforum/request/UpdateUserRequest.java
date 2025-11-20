package com.example.springforum.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;

    private String nickname;

    private String phoneNum;

    private String email;

    private Byte gender;

    private String remark;
}
