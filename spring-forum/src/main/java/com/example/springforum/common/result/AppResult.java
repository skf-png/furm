package com.example.springforum.common.result;

import com.example.springforum.common.enums.ResultCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppResult<T> {
    int code;
    String message;
    T data;
    /*
    成功
     */
    public static AppResult success() {
        return new AppResult(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(), null);
    }
    //返回数据
    public static <T> AppResult<T> success(T data) {
        return new AppResult(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(), data);
    }
    //返回数据+自定义消息
    public static <T> AppResult<T> success(T data, String message) {
        return new AppResult(ResultCode.SUCCESS.getCode(),
                message, data);
    }
    /*
    失败情况
     */
    public static <T> AppResult<T> failed() {
        return new AppResult(ResultCode.FAILED.getCode(),
                ResultCode.FAILED.getMessage(), null);
    }
    //返回异常信息
    public static <T> AppResult<T> failed(String message) {
        return new AppResult(ResultCode.FAILED.getCode(), message, null);
    }
    //返回任意错误
    public static <T> AppResult<T> failed(ResultCode resultCode) {
        return new AppResult(resultCode.getCode(), resultCode.getMessage(), null);
    }
    //返回异常数据

    public static <T> AppResult<T> failed(T data) {
        return new AppResult(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }

}
