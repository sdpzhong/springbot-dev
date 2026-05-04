package com.sdpzhong.dev.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sdpzhong.dev.entity.dto.page.IPageDto;
import com.sdpzhong.dev.entity.po.Article;
import com.sdpzhong.dev.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
@Api(tags = "开放接口", description = "该模块下接口全部开放")
public class PublicController {
    private final ArticleService articleService;

    /* 获取文章分页数据 */
    @GetMapping("/article/page")
    @ApiOperation("获取文章分页数据")
    public IPage<Article> page(@Parameter(name = "IPageDto") IPageDto dto) {
        return articleService.getArticlePublicPage(dto);
    }
}
