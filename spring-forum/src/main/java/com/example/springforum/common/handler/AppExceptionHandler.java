package com.example.springforum.common.handler;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {
    /**
     *
     * @param 处理自定义异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AppException.class)
    public AppResult handler(AppException e) {
        log.error(e.getMessage(), e);
        if (e.getAppResult() != null) {
            return e.getAppResult();
        }

        return AppResult.failed(e.getMessage());
    }

    /**
     * 处理其他异常信息
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public AppResult handler(Exception e) {
        log.error(e.getMessage(), e);
        if (e.getMessage() == null) {
            return AppResult.failed(ResultCode.ERROR_SERVICES);
        }
        return AppResult.failed(e.getMessage());
    }
}
