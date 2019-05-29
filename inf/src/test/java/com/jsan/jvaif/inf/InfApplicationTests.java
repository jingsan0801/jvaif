package com.jsan.jvaif.inf;

import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.mapper.ScyUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan({"com.jsan.jvaif.inf.mapper"})
@Slf4j
public class InfApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    private ScyUserMapper scyUserMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<ScyUser> scyUserList = scyUserMapper.selectList(null);
        Assert.assertEquals(3, scyUserList.size());
        for(ScyUser scyUser : scyUserList) {
            log.info("scyUser = {}",scyUser);
        }
    }
}
