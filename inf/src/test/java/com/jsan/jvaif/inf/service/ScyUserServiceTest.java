package com.jsan.jvaif.inf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jsan.jvaif.inf.InfApplicationTests;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.domain.ScyUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

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
    private String userName = "wangjc0801";

    @Test
    public void addUser() {
        String password = "wangjc0801";

        int rs = scyUserService.addUser(userName, password);
        log.info("addUser rs = {}", rs);
        selectByUserName(userName);
    }

    //@Test
    public void update() {
        UpdateWrapper<ScyUser> updateWrapper = new UpdateWrapper<>();
        Long id = 7L;
        updateWrapper.eq("id", id);

        ScyUser scyUser = new ScyUser();
        scyUser.setUserName("jing");
        scyUser.setPassword("123");

        log.info("before update :{}", scyUserService.getById(id));
        scyUserService.update(scyUser, updateWrapper);
        log.info("after update :{}", scyUserService.getById(id));
    }

    public void selectByUserName(String userName) {
        ScyUser rs = scyUserService.getOne(new QueryWrapper<ScyUser>().eq("user_name", userName));
        log.info("{} : {}", userName, rs);
    }

    //@Test
    public void getScyUserByNameTest() {
        ScyUser scyUser = scyUserService.getScyUserByName("wang");
        log.info("getScyUserByName : {}", scyUser);
    }

    @Test
    public void encodePasswordTest() {
        String password = "111111";
        ScyUser queryRs = scyUserService.getOne(new QueryWrapper<ScyUser>().eq("user_name", userName));
        String salt = queryRs.getSalt();
        String rs = scyUserService.shiroMd5(password, userName, salt);
        log.info("encode password = {}", rs);

        Assert.assertEquals(rs, queryRs.getPassword());
    }

    @Test
    public void getRolesByUserName() {
        Set<ScyRole> roles = scyUserService.getRoleSetByUserName(userName);
        log.info("role : {}",roles);
    }

    @Test
    public void getAuthSetByUserName() {
        Set<ScyAuth> authSet = scyUserService.getAuthSetByUserName(userName);
        Assert.assertEquals(authSet.size(),3);
        log.info("auth set: {}", authSet);
    }

}
