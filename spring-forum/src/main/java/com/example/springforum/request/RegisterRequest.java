package com.example.springforum.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;
}
