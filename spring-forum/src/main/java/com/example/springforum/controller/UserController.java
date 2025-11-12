package com.example.springforum.controller;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.common.utils.MD5Util;
import com.example.springforum.common.utils.UUIDUtil;
import com.example.springforum.model.User;
import com.example.springforum.request.RegisterRequest;
import com.example.springforum.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 用户注册
     * @param request 包括姓名，密码，昵称
     * @return
     */
    @PostMapping("/register")
    public AppResult registerUser(@Validated @RequestBody RegisterRequest request) {
        //判空
        if (request == null) {
            log.error("请求登录传参为空");
            return AppResult.failed(ResultCode.ERROR_IS_NULL);
        }
        User user = new User();
        //密码加密
        String salt = UUIDUtil.UUID32();
        String password = request.getPassword();
        user.setSalt(salt);
        user.setPassword(MD5Util.md5(password, salt));
        //设置参数
        user.setNickname(request.getNickname());
        user.setUsername(request.getUsername());
        //调用service
        userService.register(user);
        return AppResult.success();
    }
}
