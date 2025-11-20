package com.example.springforum.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
