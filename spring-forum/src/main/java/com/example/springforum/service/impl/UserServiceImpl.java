package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.UserMapper;
import com.example.springforum.model.User;
import com.example.springforum.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(User user) {
        //判空
        if (user == null
                || !StringUtils.hasText(user.getUsername())
                || !StringUtils.hasText(user.getPassword())
                || !StringUtils.hasText(user.getNickname())) {
            log.error("传参为空或部分关键内容为空");
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //验证用户名是否存在
        User user1 = userMapper.getUserByUsername(user.getUsername());
        if (user1 != null) {
            log.info("用户名"+user.getUsername() + "已存在");
            throw new AppException(AppResult.failed(ResultCode.FAILED_USER_EXISTS));
        }
        //插入新用户
        int a = userMapper.insertSelective(user);
        //如果插入数量不正确
        if (a != 1) {
            log.error("新增失败，新增数量为" + a);
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }
}
