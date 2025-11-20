package com.example.springforum.service.impl;

import com.example.springforum.common.result.AppResult;
import com.example.springforum.model.User;
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

    @Test
    void selectByUserId() {
        User user = userService.selectByUserId(5l);
        System.out.println(user.toString());
    }

    @Test
    void updateUserInfo() {
        User user = new User();
        user.setId(5l);
        user.setNickname("张三四");
        userService.updateUserInfo(user);
    }

    @Test
    void updatePassword() {
        userService.updatePassword(4l,"1234", "123");
    }
}
