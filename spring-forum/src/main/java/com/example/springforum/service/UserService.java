package com.example.springforum.service;

import com.example.springforum.common.result.AppResult;
import com.example.springforum.model.User;

public interface UserService {
    public void register(User user);

    public User login(String username,String password);

    public User selectByUserId(Long userId);

    public void addOneArticleCount(Long id);

}
