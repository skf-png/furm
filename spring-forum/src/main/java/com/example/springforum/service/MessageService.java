package com.example.springforum.service;

import com.example.springforum.model.DTO.MessageDTO;
import com.example.springforum.model.Message;

import java.util.List;

public interface MessageService {
    void addMessage(Message message);

    Integer getUnreadCount(Long id);

    List<MessageDTO> getMessagesByUserId(Long id);

    void updateStateById(Long id, Byte state);

    Message getMessage(Long id);

    void replyMessage(Long id, Message message);
}
