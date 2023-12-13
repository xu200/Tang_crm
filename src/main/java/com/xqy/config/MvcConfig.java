package com.xqy.config;

import com.xqy.interceptor.XqyUnLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author xu
 * @Date 2023/9/30 13:11
 * @Version 1.0
 * //自定义拦截器
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean  //把你写的自定义拦截器实例注入进来
    public XqyUnLoginInterceptor unLoginInterceptor() {
        return new XqyUnLoginInterceptor();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(unLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/error", "/index", "/user/login", "/css/**", "/images/**", "/js/**", "/lib/**");
    }
}
