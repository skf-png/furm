package com.example.springforum.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateArticleRequest {
    @NotNull
    Long id;
    @NotBlank
    String title;
    @NotBlank
    String content;
}
