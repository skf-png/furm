package com.example.springforum.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStateRequest {
    @NotNull
    Long id;
    @NotNull
    Byte state;
}
