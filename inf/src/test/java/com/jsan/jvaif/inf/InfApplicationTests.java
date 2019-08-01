package com.jsan.jvaif.inf;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan({"com.jsan.jvaif.inf.mapper"})
@Slf4j
public class InfApplicationTests {

    @Before
    public void init() {
        log.info("base test before");
    }

    @After
    public void after() {
        log.info("base test after");
    }

}
