package com.example.springforum.controller;

import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/t1")
    public String t1() throws Exception{
        throw new Exception("其他异常");
    }


    @GetMapping("/t2")
    public String t2() {
        throw new AppException("自定义异常");
    }



    @GetMapping("/t3")
    public AppResult t3( String a) {
        return AppResult.success(a);
    }

}
