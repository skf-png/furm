package com.example.springforum.controller;

import com.example.springforum.common.constants.Constant;
import com.example.springforum.common.enums.ResultCode;
import com.example.springforum.common.exception.AppException;
import com.example.springforum.common.result.AppResult;
import com.example.springforum.mapper.ArticleMapper;
import com.example.springforum.model.Article;
import com.example.springforum.model.DTO.ArticleDTO;
import com.example.springforum.model.DTO.ArticleDetailDTO;
import com.example.springforum.model.User;
import com.example.springforum.request.ArticleRequest;
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
    /**
     * 传入boardid，返回对应的数据，按照时间顺序返回。
     * 如果id为空，返回所有，否则返回对应板块
     */
    @GetMapping("/getArticles")
    public AppResult getArticles(Long boardId) {
        //不同情况
        List<ArticleDTO> articleDTOS = null;
        if (boardId != null) {
            articleDTOS = articleService.getArticlesByBoardId(boardId);
//            TODO 板块不存在情况
        } else  {
            articleDTOS = articleService.getAllArticles();
        }

        return AppResult.success(articleDTOS);
    }

    @GetMapping("/getDetail")
    public AppResult getDetail(@NotNull Long id) {
        ArticleDetailDTO articleDetail = articleService.getArticleDetail(id);
        if (articleDetail == null) {
            throw new AppException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }
        return AppResult.success(articleDetail);
    }
}
