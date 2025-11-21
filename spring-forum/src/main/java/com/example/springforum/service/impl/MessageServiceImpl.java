package com.example.springforum.service.impl;

import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.MessageMapper;
import com.example.springforum.mapper.UserMapper;
import com.example.springforum.model.DTO.MessageDTO;
import com.example.springforum.model.Message;
import com.example.springforum.model.User;
import com.example.springforum.service.MessageService;
import com.example.springforum.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    MessageMapper messageMapper;
    @Resource
    UserService userService;

    @Override
    public void addMessage(Message message) {
        if (message == null || message.getPostUserId() == null || message.getReceiveUserId() == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //判断接收方是否存在
        User receiveUser = userService.selectByUserId(message.getReceiveUserId());
        if (receiveUser == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //更新
        int row = messageMapper.insertSelective(message);

    }

    @Override
    public Integer getUnreadCount(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        Integer row = messageMapper.getUnreadCount(id);
        if (row == null)
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        return row;
    }

    @Override
    public List<MessageDTO> getMessagesByUserId(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        List<MessageDTO> messageDTOS = messageMapper.getMessageByUserId(id);
        return messageDTOS;
    }

    @Override
    public void updateStateById(Long id, Byte state) {
        if (id == null || id <= 0 || state == null || state < 0 ||  state > 2) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        Message message = new Message();
        message.setId(id);
        message.setState(state);

        int row =  messageMapper.updateByPrimaryKeySelective(message);
        if (row != 1) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }

    @Override
    public Message getMessage(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        Message message = messageMapper.selectByPrimaryKey(id);

        return message;
    }

    /**
     *
     * @param id 要回复的文章id
     * @param message 回复的对象
     */
    @Transactional
    @Override
    public void replyMessage(Long id, Message message) {
        if (id == null || id <= 0) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        Message databaseMessage = messageMapper.selectByPrimaryKey(id);
        if (databaseMessage == null ||  databaseMessage.getDeleteState() == null) {
            throw new AppException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        updateStateById(id, (byte)2);
        addMessage(message);
    }
}
