package com.jsan.jvaif.inf;

import com.jsan.jvaif.inf.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @description: 引入redis的测试类
 * @author: jcwang
 * @create: 2019-08-11 21:17
 **/
@Slf4j
public class RedisUtilsTest extends InfApplicationTests {
    @Resource
    private RedisService redisService;

    @Test
    public void testSetAndGet() {
        redisService.set("javif_token_wangjc0801", 123);
        log.info(redisService.get("javif_token_wangjc0801"));
    }
}
