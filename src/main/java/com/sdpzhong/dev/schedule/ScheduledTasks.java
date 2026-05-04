package com.sdpzhong.dev.schedule;

import com.sdpzhong.dev.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: zhongqing
 * @Description: 全局定时任务配置
 * @Date: 2024-07-18 11:18
 **/

@Slf4j
@Component
public class ScheduledTasks {

    // 使用fixedRate属性来指定任务执行的固定间隔（以毫秒为单位）
    // @Scheduled(fixedRate = 5000)
    // public void reportCurrentTimeWithFixedRate() {
    //     System.out.println("当前时间（fixedRate）: " + System.currentTimeMillis());
    // }

    private final ArticleService articleService;

    public ScheduledTasks(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 定时更新文章状态
     */
//    @Scheduled(cron = "1 1/1 * * * ?")
    @Scheduled(fixedRate = 30 * 1000)
    public void autoRefreshArticleStatusTask() {
        articleService.refreshArticlePendingPublishStatusRecords();
    }

    /* 定时审核 */

    @PostConstruct
    public void init() {
        log.info("ScheduledTasks init");
    }

    @PreDestroy
    public void destroy() {
        log.info("ScheduledTasks destroy");
    }

}