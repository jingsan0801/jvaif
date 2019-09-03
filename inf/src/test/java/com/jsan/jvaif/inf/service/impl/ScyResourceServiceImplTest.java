package com.jsan.jvaif.inf.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsan.jvaif.inf.InfApplicationTests;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.dto.ScyResourceDTO;
import com.jsan.jvaif.inf.service.IScyResourceService;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * ScyResourceServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>9月 3, 2019</pre>
 */
@Slf4j
public class ScyResourceServiceImplTest extends InfApplicationTests {

    @Resource
    private IScyResourceService scyResourceService;
    @Resource
    private IScyUserService scyUserService;

    @Test
    public void testGetAllMenuByRole() throws Exception {
        Set<ScyRole> roles = scyUserService.getRoleSetByUserName("wangjc0801");
        if (roles != null && roles.size() > 0) {
            List<ScyResourceDTO> resources = scyResourceService.getAllMenuByRole(roles);
            log.info("resources = {}", JSON.toJSONString(resources));
        }


    }

} 
