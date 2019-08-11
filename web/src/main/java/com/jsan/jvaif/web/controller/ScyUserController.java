package com.jsan.jvaif.web.controller;

import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import com.jsan.jvaif.web.annotation.SkipToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 按userId获取ScyUser
     *
     * @param userId userId
     * @return ScyUser
     */
    @SkipToken
    @RequestMapping(value = "/{ScyUserId}", method = RequestMethod.GET)
    public Result getScyUserById(
        @PathVariable("ScyUserId")
            String userId) {
        ScyUser scyUser = scyUserService.getById(userId);
        return ResultUtil.success(scyUser);
    }

    /**
     * 按userName获取ScyUser
     *
     * @param name userName
     * @return ScyUser
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result getScyUserByName(
        @RequestParam("name")
            String name) {
        ScyUser scyUser = scyUserService.getScyUserByName(name);
        return ResultUtil.success(scyUser);
    }

    /**
     * 新增一个用户
     *
     * @param userName 用户名
     * @param password 密码
     * @return Result
     */
    @PostMapping(value = "")
    public Result addUser(
        @RequestParam("userName")
            String userName,
        @RequestParam("password")
            String password) {
        return scyUserService.addUser(userName, password);
    }

}
