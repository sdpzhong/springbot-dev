package com.sdpzhong.dev.config;


import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.sdpzhong.dev.http.HttpInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有路径应用跨域配置
                .allowedOriginPatterns("*") // 允许任何域名
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 是否允许证书（cookies）
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器
        registry.addInterceptor(new HttpInterceptor());

        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        // registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");

        // 全局路由鉴权拦截器
        registry.addInterceptor(
                        new SaInterceptor(handle -> StpUtil.checkLogin())
                                // 关闭注解鉴权 @SaCheckLogin
                                .isAnnotation(false)
                )
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/logout",
                        "/user/register",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**",
                        "/public/**"
                );
    }


}