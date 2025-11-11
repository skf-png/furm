package com.example.springforum.controller;

import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试接口", description = "测试")
@RestController
@RequestMapping("/test")
public class TestController {
    @Operation(
            summary = "测试其他异常",
            description = "测试其他异常"
    )
    @GetMapping("/t1")
    public String t1() throws Exception{
        throw new Exception("其他异常");
    }

    @Operation(
            summary = "测试",
            description = "测试自定义异常"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功，返回用户数据"),
            @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    @GetMapping("/t2")
    public String t2() {
        throw new AppException("自定义异常");
    }

    @GetMapping("/t3")
    public AppResult t3(@Parameter(description = "一个字符串") String a) {
        return AppResult.success(a);
    }
}
