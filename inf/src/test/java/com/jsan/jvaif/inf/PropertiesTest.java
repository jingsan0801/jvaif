package com.jsan.jvaif.inf;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 读取配置文件测试类
 * @author: jcwang
 * @create: 2019-08-12 21:02
 **/
@Slf4j
public class PropertiesTest extends InfApplicationTests {

    @Value("${shiro.filter_anno.urls}")
    private String urls;

    @Value("#{'${shiro.filter_anno.urls}'.split(',')}")
    private List<String> urlList;

    @Test
    public void getProperties() {
        log.info("urls = {}",urls);
        log.info("urlList = {}",urlList);
    }
}
