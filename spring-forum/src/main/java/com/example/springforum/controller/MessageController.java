package com.example.springforum.controller;

import com.example.springforum.common.constants.Constant;
import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.model.DTO.MessageDTO;
import com.example.springforum.model.Message;
import com.example.springforum.model.User;
import com.example.springforum.request.SendMessageRequest;
import com.example.springforum.request.UpdateStateRequest;
import com.example.springforum.service.MessageService;
import com.example.springforum.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;

    @PostMapping("/send")
    public AppResult sendMessage(HttpServletRequest request,
                                 @RequestBody @Validated SendMessageRequest sendMessageRequest) {
        HttpSession session = request.getSession();
        //获取用户
        User user =  (User) session.getAttribute(Constant.USER_SESSION_KEY);
        //是否禁言
        if (user.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //不能给自己发送信息
        if (user.getId() == sendMessageRequest.getId()) {
            return AppResult.failed("不能给自己发送");
        }
        //接受者是否存在
        User receiveUser = userService.selectByUserId(sendMessageRequest.getId());
        if (receiveUser == null) {
            return AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        //封装对象
        Message message = new Message();
        message.setContent(sendMessageRequest.getContent());
        message.setReceiveUserId(sendMessageRequest.getId());
        message.setPostUserId(user.getId());
        //插入数据库
        messageService.addMessage(message);

        return AppResult.success();
    }

    @GetMapping("/getUnreadCount")
    public AppResult getUnreadCount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user =  (User) session.getAttribute(Constant.USER_SESSION_KEY);

        Integer res =  messageService.getUnreadCount(user.getId());
        return AppResult.success(res);
    }

    @GetMapping("/getMessagesByUserId")
    public AppResult getMessagesByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user =  (User) session.getAttribute(Constant.USER_SESSION_KEY);

        List<MessageDTO> result = messageService.getMessagesByUserId(user.getId());
        return AppResult.success(result);
    }

    @PostMapping("/updateState")
    public AppResult updateState(HttpServletRequest request,
            @Validated @RequestBody UpdateStateRequest updateStateRequest) {
        Message message = messageService.getMessage(updateStateRequest.getId());
//        System.out.println(message.toString());
        if (message == null || message.getDeleteState() == 1) {
            return AppResult.failed(ResultCode.FAILED_NOT_EXISTS);
        }

        HttpSession session = request.getSession(false);
        User user =  (User) session.getAttribute(Constant.USER_SESSION_KEY);
        //不是自己的信息，无权修改
        if (message.getReceiveUserId() != user.getId()) {
            return  AppResult.failed(ResultCode.FAILED_UNAUTHORIZED);
        }

        messageService.updateStateById(updateStateRequest.getId(), updateStateRequest.getState());
        return AppResult.success();
    }

    @PostMapping("/reply")
    public AppResult reply(HttpServletRequest request, @RequestBody @Validated SendMessageRequest sendMessageRequest) {
        HttpSession session = request.getSession(false);
        User user =  (User) session.getAttribute(Constant.USER_SESSION_KEY);
        if (user.getDeleteState() == 1) {
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //获取被回复的消息
        Message message = messageService.getMessage(sendMessageRequest.getId());
        if (message == null || message.getDeleteState() == 1) {
            return AppResult.failed(ResultCode.FAILED_NOT_EXISTS);
        }
        if (message.getPostUserId() ==  user.getId()) {
            return AppResult.failed("不能回复自己");
        }
        //封装回复的消息：postId,receiveId,content
        Message replyMessage = new Message();
        replyMessage.setContent(sendMessageRequest.getContent());
        replyMessage.setReceiveUserId(message.getPostUserId());
        replyMessage.setPostUserId(message.getReceiveUserId());
        //回复
        messageService.replyMessage(sendMessageRequest.getId(), replyMessage);

        return AppResult.success();
    }
}
