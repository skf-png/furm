package com.example.springforum.controller;

import com.example.springforum.common.constants.Constant;
import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.ArticleMapper;
import com.example.springforum.model.Article;
import com.example.springforum.model.User;
import com.example.springforum.request.ArticleRequest;
import com.example.springforum.service.ArticleService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping("/addArticle")
    public AppResult addArticle(HttpServletRequest request,
                                        @RequestBody @Validated ArticleRequest articleRequest) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Constant.USER_SESSION_KEY);

        if (user.getState() == 1) {
            // 用户已禁言
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        article.setBoardId(articleRequest.getBoardId());
        article.setUserId(user.getId());

        log.info(articleRequest.toString());
        //插入文章
        articleService.addArticle(article);
        return AppResult.success();
    }
}
