package com.sdpzhong.dev;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
// log
@Slf4j
// mapper文件扫描（省去 @Mapper 注解）
@MapperScan("com.sdpzhong.dev.mapper")
// nacos 配置文件
@NacosPropertySource(dataId = "application-dev", autoRefreshed = true, type = ConfigType.YAML)
// 允许 nacos 客户端被服务端扫描并获取
@EnableDiscoveryClient
// 异步支持
@EnableAsync
// 事务支持
@EnableTransactionManagement
// 定时任务
@EnableScheduling
public class DevApplication {

    public static void main(String[] args) {

        // SpringApplication.run(DevApplication.class, args);

        SpringApplication app = new SpringApplication(DevApplication.class);
        Environment environment = app.run(args).getEnvironment();

        log.info("Application started successfully!!");
        log.info("Address: http://127.0.0.1:{}", environment.getProperty("server.port"));
        log.info("Api doc address: http://127.0.0.1:{}/swagger-ui/index.html", environment.getProperty("server.port"));
        log.info("nacos address: http://127.0.0.1:8080");
    }

}



