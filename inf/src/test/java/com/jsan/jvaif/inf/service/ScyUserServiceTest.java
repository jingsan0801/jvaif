package com.jsan.jvaif.inf.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jsan.jvaif.inf.InfApplicationTests;
import com.jsan.jvaif.inf.domain.ScyUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-08-01 21:51
 **/
@Slf4j
//按照代码顺序执行test
@FixMethodOrder(MethodSorters.JVM)
public class ScyUserServiceTest extends InfApplicationTests {

    @Autowired
    private IScyUserService scyUserService;

    @Test
    public void insert() {
        ScyUser scyUser = new ScyUser();
        scyUser.setName("wang");
        scyUser.setPassword("123");

        boolean insertRsFlag = scyUserService.save(scyUser);
        log.info("insert rs flag = {}", insertRsFlag);
        selectAll();

    }

    @Test
    public void update() {
        UpdateWrapper<ScyUser> updateWrapper = new UpdateWrapper<>();
        Long id = 7L;
        updateWrapper.eq("id", id);

        ScyUser scyUser = new ScyUser();
        scyUser.setName("jing");
        scyUser.setPassword("123");

        log.info("before update :{}", scyUserService.getById(id));
        scyUserService.update(scyUser, updateWrapper);
        log.info("after update :{}", scyUserService.getById(id));
    }

    public void selectAll() {
        List<ScyUser> rs = scyUserService.list();
        log.info("list all = {}", rs);
    }

}
