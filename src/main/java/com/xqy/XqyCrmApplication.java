package com.xqy;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com/xqy/dao")
@Slf4j
@EnableCaching //开启了SpringBoot基于注解的缓存管理实现
public class XqyCrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(XqyCrmApplication.class, args);
        log.info("唐三藏CRM项目启动成功！！！");
    }
}
