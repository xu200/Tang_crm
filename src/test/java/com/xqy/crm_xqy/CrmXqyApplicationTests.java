package com.xqy.crm_xqy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootTest
class CrmXqyApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("redis")
    public String testRedis() {

        String test = redisTemplate.opsForValue().get("test");
        System.out.println(test);
        return test;

    }
}
