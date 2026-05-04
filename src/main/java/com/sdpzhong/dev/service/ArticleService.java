package com.sdpzhong.dev.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sdpzhong.dev.entity.dto.page.IPageDto;
import com.sdpzhong.dev.entity.po.Article;

/**
 * @author zhongqing
 * @description 针对表【t_article】的数据库操作Service
 * @createDate 2024-07-16 17:19:54
 */
public interface ArticleService extends IService<Article> {


    /**
     * 获取文章分页
     *
     * @param dto
     * @return
     */
    Page<Article> getArticlePage(IPageDto dto);

    /**
     * 获取文章分页数据，开发接口
     *
     * @param dto
     * @return
     */
    Page<Article> getArticlePublicPage(IPageDto dto);


    /* 发布文章 */
    /*    Boolean publishArticle();*/


    /* 变更文章状态 */

    /* 修改文章 */

    /* 删除文章 */


    /**
     * 自动更新定时发布状态的文章为发布态
     */
    void refreshArticlePendingPublishStatusRecords();
}
