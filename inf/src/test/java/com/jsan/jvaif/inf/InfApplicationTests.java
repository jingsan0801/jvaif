package com.jsan.jvaif.inf;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.service.IScyUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan({"com.jsan.jvaif.inf.mapper"})
@Slf4j
public class InfApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private IScyUserService scyUserService;

    @Test
    public IPage<ScyUser> page() {
        QueryWrapper<ScyUser> query = new QueryWrapper<ScyUser>();
        query.ge("id", 1);

        Page<ScyUser> page = new Page<>(1, 2);
        IPage<ScyUser> rs = scyUserService.page(page);

        log.info("pageRs = {}", rs);
        return rs;
    }
}
