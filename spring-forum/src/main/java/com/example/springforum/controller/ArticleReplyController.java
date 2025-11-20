package com.example.springforum.controller;

import com.example.springforum.common.constants.Constant;
import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.ArticleMapper;
import com.example.springforum.mapper.ArticleReplyMapper;
import com.example.springforum.model.Article;
import com.example.springforum.model.ArticleReply;
import com.example.springforum.model.DTO.ArticleReplyDTO;
import com.example.springforum.model.User;
import com.example.springforum.request.AddReplyRequest;
import com.example.springforum.service.ArticleReplyService;
import com.example.springforum.service.ArticleService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@Slf4j
public class ArticleReplyController {
    @Resource
    private ArticleReplyService articleReplyService;
    @Resource
    private ArticleService articleService;

    @PostMapping("/add")
    public AppResult addReply(HttpServletRequest request,
                              @RequestBody @Validated AddReplyRequest addReplyRequest) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_SESSION_KEY);
        if (user.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        ArticleReply articleReply = new ArticleReply();
        articleReply.setArticleId(addReplyRequest.getArticleId());
        articleReply.setContent(addReplyRequest.getContent());
        articleReply.setPostUserId(user.getId());
        //添加
        articleReplyService.addReply(articleReply);

        return AppResult.success();
    }

    @GetMapping("/getReply")
    public AppResult getReply(@RequestParam @NotNull Long articleId) {
        List<ArticleReplyDTO> result = articleReplyService.selectByArticleId(articleId);
        return AppResult.success(result);
    }
}
