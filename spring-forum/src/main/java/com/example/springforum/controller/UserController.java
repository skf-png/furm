package com.example.springforum.controller;

import com.example.springforum.common.constants.Constant;
import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.common.utils.MD5Util;
import com.example.springforum.common.utils.UUIDUtil;
import com.example.springforum.model.User;
import com.example.springforum.request.LoginRequest;
import com.example.springforum.request.RegisterRequest;
import com.example.springforum.request.UpdatePasswordRequest;
import com.example.springforum.request.UpdateUserRequest;
import com.example.springforum.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户登录
     * @param request servlet
     * @param loginRequest username password
     * @return
     */
    @PostMapping("/login")
    public AppResult login(HttpServletRequest request,
                           @Validated @RequestBody LoginRequest loginRequest) {
        //判空
        if (loginRequest == null) {
            log.info("登录传参为空");
            return AppResult.failed(ResultCode.ERROR_IS_NULL);
        }
        //执行登录操作
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        //检查是否登录成功
        if (user == null) {
            log.warn(ResultCode.ERROR_SERVICES.toString());
            return AppResult.failed(ResultCode.ERROR_SERVICES);
        }
        //设置session
        HttpSession session = request.getSession(true);
        session.setAttribute(Constant.USER_SESSION_KEY, user);
        //成功返回
        log.info("用户" + user.getUsername() + "已上线");
        return AppResult.success();
    }

    @GetMapping("/getUser")
    public AppResult getUser(Long userId, HttpServletRequest request) {
        //检查有没有登录
        HttpSession session = request.getSession(false);
        User user = null;
        //如果id为空返回自身的user信息,如果id不为空返回对应的user信息
        if (userId == null) {
            user = (User) session.getAttribute(Constant.USER_SESSION_KEY);
        } else {
            user = userService.selectByUserId(userId);
        }
        return AppResult.success(user);
    }
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public AppResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return AppResult.success();
    }

    @PostMapping("/updateUserInfo")
    public AppResult updateUserInfo(HttpServletRequest request,
                                    @RequestBody @NotNull UpdateUserRequest userInfo) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Constant.USER_SESSION_KEY);
        User updateUser = new User();
        //封装
        updateUser.setId(user.getId());
        updateUser.setUsername(userInfo.getUsername());
        updateUser.setNickname(userInfo.getNickname());
        updateUser.setEmail(userInfo.getEmail());
        updateUser.setGender(userInfo.getGender());
        updateUser.setRemark(userInfo.getRemark());
        updateUser.setPhoneNum(userInfo.getPhoneNum());
        //更新
        User user1 = userService.updateUserInfo(updateUser);
        session.setAttribute(Constant.USER_SESSION_KEY, user1);

        return AppResult.success();
    }
    @PostMapping("/updatePassword")
    public AppResult updatePassword(HttpServletRequest request,
                                    @RequestBody @Validated UpdatePasswordRequest updatePasswordRequest) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Constant.USER_SESSION_KEY);
        userService.updatePassword(user.getId(), updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword());
        return AppResult.success();
    }
}
