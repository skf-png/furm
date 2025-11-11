package com.example.springforum.common.exception;

import com.example.springforum.common.result.AppResult;
import lombok.Data;

@Data
public class AppException extends RuntimeException {
    private AppResult appResult;
    public AppException(AppResult appResult) {
        super(appResult.getMessage());
        this.appResult = appResult;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }
}
