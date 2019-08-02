package com.jsan.jvaif.web.controller;

import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 用户controller
 * @author: jcwang
 * @create: 2019-08-01 21:12
 **/

@RestController
@RequestMapping("/scy/user")
@Slf4j
public class ScyUserController {

    @Resource
    private IScyUserService scyUserService;

    @RequestMapping("/{ScyUserName}")
    public Result getScyUserByName(
        @PathVariable("ScyUserName")
            String userName) {
        ScyUser scyUser = scyUserService.getScyUserByName(userName);
        return ResultUtil.success(scyUser);
    }

}
