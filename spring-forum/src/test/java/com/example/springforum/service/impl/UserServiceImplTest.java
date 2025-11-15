package com.example.springforum.service.impl;

import com.example.springforum.common.result.AppResult;
import com.example.springforum.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Resource(name = "userServiceImpl")
    UserService userService;
    @Test
    void login() {
        String username = "123";
        String password = "123";
        userService.login(username, password);
    }
}
