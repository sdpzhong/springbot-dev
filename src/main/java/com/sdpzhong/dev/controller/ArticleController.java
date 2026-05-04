package com.sdpzhong.dev.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/article")
@Api(tags = "文章模块", description = "文章相关接口")
public class ArticleController {

    private final ArticleService articleService;

    // @Autowired
    // UserBean userBean;
    // @Resource
    // ComponentTest componentTest;

    /* 获取文章分页数据 */
    @GetMapping("/page")
    @ApiOperation("获取当前用户所有文章分页数据")
    public Page<Article> page(@Parameter(name = "IPageDto") IPageDto dto) {
        return articleService.getArticlePage(dto);
    }


    /* 检索文章 */

    /* 获取文章详情 */

    /* 文章热门排行（阅读量、点赞、收藏、转发） */

    /* 发布文章 */

    /* 删除文章 */

    /* 获取个人文章分页数据 */

    /* 修改文章状态 */
}
