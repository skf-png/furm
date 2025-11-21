package com.example.springforum.service.impl;

import com.example.springforum.model.DTO.MessageDTO;
import com.example.springforum.model.Message;
import com.example.springforum.service.MessageService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageServiceImplTest {
    @Resource
    private MessageService messageService;

    @Test
    void addMessage() {
        Message message = new Message();
        message.setPostUserId(4l);
        message.setReceiveUserId(5l);
        message.setContent("666");
        messageService.addMessage(message);
    }

    @Test
    void getUnreadCount() {
        int res = messageService.getUnreadCount(4l);
        System.out.println(res);
    }

    @Test
    void getMessagesByUserId() {
        List<MessageDTO>  messageDTOS = messageService.getMessagesByUserId(4l);
        System.out.println(messageDTOS);
    }

    @Test
    void updateStateById() {
        messageService.updateStateById(1l, (byte)0);
    }
}
