package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.common.utils.MD5Util;
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


    @Override
    public User login(String username, String password) {
        //判空
        if (username == null || password == null) {
            log.info(ResultCode.ERROR_IS_NULL.toString());
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //根据用户名查询
        User user = userMapper.getUserByUsername(username);
        //如果没找不到
        if (user == null) {
            log.info("username:" + username + ResultCode.FAILED_NOT_EXISTS.toString());
            throw new AppException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        //判断密码是否正确
        if (!MD5Util.checkMd5(user.getPassword(),user.getSalt(),password)) {
            log.warn(ResultCode.FAILED_LOGIN.toString());
            throw new AppException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }
        //正确的话，返回...
        return user;
    }

    /**
     * 根据id查找用户
     * @param userId 用户id
     * @return
     */
    @Override
    public User selectByUserId(Long userId) {
        //判空
        if (userId == null) {
            log.info(ResultCode.ERROR_IS_NULL.toString());
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //查询
        User user = userMapper.selectByPrimaryKey(userId);
        //如果没找到
        if (user == null) {
            log.info(ResultCode.FAILED_NOT_EXISTS.toString() + userId);
            throw new AppException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }
        //成功
        return user;
    }

    /**
     * 用户作品数量+1
     * @param id
     */
    @Override
    public void addOneArticleCount(Long id) {
        //判空
        if (id == null) {
            log.info(ResultCode.ERROR_IS_NULL.toString());
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        User user = userMapper.selectByPrimaryKey(id);

        if (user == null) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setArticleCount(user.getArticleCount() + 1);
        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if (row != 1) {
            log.warn("影响行数不为1");
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }
}
