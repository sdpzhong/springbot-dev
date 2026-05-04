package com.sdpzhong.dev.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: zhongqing
 * @Description: Mybatis-plus 配置文件, 表数据填充的实现类，如： @TableField(value = "uid", fill = FieldFill.INSERT)
 * @Date: 2024-07-15 17:24
 **/

@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 阻止全表更新与删除的操作（拦截器）
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }


    // 表数据自动填充

    /**
     * 实现创建时间&更新时间字段自动更新
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("uid")) {
            String uid = UUID.randomUUID().toString().replace("-", "");
            this.strictInsertFill(metaObject, "uid", String.class, uid);
        }
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }

}