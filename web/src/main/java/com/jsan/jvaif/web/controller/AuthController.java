package com.jsan.jvaif.web.controller;

import com.jsan.jvaif.common.util.JsonUtil;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.JwtUtil;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.jsan.jvaif.inf.constant.ResultEnum.exception_authentication;
import static com.jsan.jvaif.inf.constant.ResultEnum.success_login;

/**
 * @description: 身份验证相关controller
 * @author: jcwang
 * @create: 2019-08-03 21:27
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private IScyUserService scyUserService;

    @PostMapping(value = "/login")
    public Result login(
        @RequestParam(name = "userName")
            String userName,
        @RequestParam(name = "password")
            String password) {
        ScyUser scyUser = scyUserService.getScyUserByName(userName);
        String salt = scyUser.getSalt();
        // 计算加密后的密码
        String encodePassword = scyUserService.shiroMd5(password, userName, salt);
        if (encodePassword.equals(scyUser.getPassword())) {
            String token = JwtUtil.sign(userName, encodePassword);
            Map<String, Object> rsMap = new HashMap<>(1);
            rsMap.put("token", token);
            return ResultUtil.success(success_login, JsonUtil.mapToJsonObject(rsMap));
        }
        return ResultUtil.fail(exception_authentication, null);
    }
}