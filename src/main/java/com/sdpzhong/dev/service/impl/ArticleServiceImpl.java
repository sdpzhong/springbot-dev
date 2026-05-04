package com.sdpzhong.dev.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdpzhong.dev.common.ArticleStatus;
import com.sdpzhong.dev.entity.dto.page.IPageDto;
import com.sdpzhong.dev.entity.po.Article;
import com.sdpzhong.dev.mapper.ArticleMapper;
import com.sdpzhong.dev.service.ArticleService;
import com.sdpzhong.dev.utils.MpQueryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhongqing
 * @description 针对表【t_article】的数据库操作Service实现
 * @createDate 2024-07-16 17:19:54
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    private final ArticleMapper articleMapper;

    @Override
    public Page<Article> getArticlePage(IPageDto dto) {
        Page<Article> articlePage = new Page<>(dto.getCurrent(), dto.getSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        wrapper.eq("uid", StpUtil.getLoginId());

        // 筛选
        List<String> allowFilters = Lists.newArrayList("channelId", "content", "createTime", "keywords", "readCount", "status", "subtitle", "title");
        MpQueryUtil.buildFilter(wrapper, Article.class, dto.getFilters(),
                allowFilters);
        // 排序
        List<String> allowSorts = Arrays.asList("createTime", "readCount", "title", "channelId", "updateTime", "status");
        MpQueryUtil.buildSort(wrapper, Article.class, dto.getSorts(),
                allowSorts,
                "createTime", true);

        return page(articlePage, wrapper);
    }

    @Override
    public Page<Article> getArticlePublicPage(IPageDto dto) {
        Page<Article> articlePage = new Page<>(dto.getCurrent(), dto.getSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        // 未删除 && 已发布的文章
        wrapper.eq("deleted", 0);
        wrapper.eq("status", ArticleStatus.PUBLISHED.getStatus());

        // filters
        List<String> allowFilters = Lists.newArrayList("viewCount", "title", "subtitle", "keywords", "channelId", "content", "createTime");
        MpQueryUtil.buildFilter(wrapper, Article.class, dto.getFilters(),
                allowFilters);

        // sorts
        List<String> allowSorts = Arrays.asList("createTime", "viewCount", "title", "channelId");
        MpQueryUtil.buildSort(wrapper, Article.class, dto.getSorts(),
                allowSorts,
                "createTime", true);

        return articlePage;
    }

    /**
     * 未设置发布时间文章，默认将提交时间作为发布时间
     */
    @Transactional
    @Override
    public void refreshArticlePendingPublishStatusRecords() {

        log.info("==== refreshArticlePendingPublishStatusRecords ====");

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper
                .eq(Article::getStatus, ArticleStatus.PENDING_PUBLISH.getStatus())
                .and(
                        qw -> qw.isNull(Article::getPublishTime)
                                .or()
                                .lt(Article::getPublishTime, new Date())
                )
                .select(Article::getId);

        List<Article> articles = list(queryWrapper);

        if (!articles.isEmpty()) {
            // 更新文章状态为发布态
            articles.forEach(article -> article.setStatus(ArticleStatus.PUBLISHED.getStatus()));
            updateBatchById(articles);
        }

    }
}




