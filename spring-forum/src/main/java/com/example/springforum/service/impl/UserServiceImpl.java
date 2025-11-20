package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.common.utils.MD5Util;
import com.example.springforum.common.utils.UUIDUtil;
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

    @Override
    public void subOneArticleCount(Long id) {
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
        updateUser.setArticleCount(user.getArticleCount() - 1);
        //如果小于0
        if (updateUser.getArticleCount() < 0) {
            updateUser.setArticleCount(0);
        }

        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if (row != 1) {
            log.warn("影响行数不为1");
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

    }

    @Override
    public User updateUserInfo(User user) {
        if (user == null || user.getId() == null || user.getId() < 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //判断用户是否存在
        User user1 = userMapper.selectByPrimaryKey(user.getId());
        if (user1 == null) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }
        //校验参数
        User updateUser = new User();
        updateUser.setId(user.getId());
        boolean isUpdate = false;
        //检验用户名是否合法
        if (StringUtils.hasLength(user.getUsername())
                && !user.getUsername().equals(user1.getUsername())) {
            //判断用户名是否重复
            User user2 = userMapper.getUserByUsername(user.getUsername());
            if (user2 != null) {
                throw new AppException(AppResult.failed(ResultCode.FAILED_USER_EXISTS));
            }
            updateUser.setUsername(user.getUsername());
            isUpdate = true;
        }

        //检验昵称
        if (StringUtils.hasLength(user.getNickname())
        && !user.getNickname().equals(user1.getNickname())) {
            updateUser.setNickname(user.getNickname());
            isUpdate = true;
        }
        //检查邮箱
        if (StringUtils.hasLength(user.getEmail())
        && !user.getEmail().equals(user1.getEmail())) {
            updateUser.setEmail(user.getEmail());
            isUpdate = true;
        }
        //检查手机号
        if (StringUtils.hasLength(user.getPhoneNum())
        && !user.getPhoneNum().equals(user1.getPhoneNum())) {
            updateUser.setPhoneNum(user.getPhoneNum());
            isUpdate = true;
        }
        //个人简介
        if (user.getRemark() != null && !user.getRemark().equals(user1.getRemark())) {
            updateUser.setRemark(user.getRemark());
            isUpdate = true;
        }
        //检验是否更新
        if (!isUpdate) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //更新
        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if (row != 1) {
            log.warn("update row: " + row);
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

        return userMapper.selectByPrimaryKey(user.getId());
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null || user.getDeleteState() == 1) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        //判断输入密码是否正确
        if (!MD5Util.checkMd5(user.getPassword(), user.getSalt(),oldPassword)) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //生成新的salt
        String salt = UUIDUtil.UUID32();
        String encryptPassword = MD5Util.md5(newPassword, salt);
        //封装
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(encryptPassword);
        updateUser.setSalt(salt);
        //更新
        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if (row != 1) {
            log.warn("update row: " + row);
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

    }
}
