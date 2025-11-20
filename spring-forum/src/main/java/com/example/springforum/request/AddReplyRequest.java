package com.example.springforum.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddReplyRequest {
    @NotNull
    Long articleId;
    @NotBlank
    String content;
}
