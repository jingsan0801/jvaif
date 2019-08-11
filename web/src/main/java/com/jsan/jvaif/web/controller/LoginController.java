package com.jsan.jvaif.web.controller;

import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import com.jsan.jvaif.web.annotation.SkipToken;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jsan.jvaif.inf.constant.ResultEnum.exception_authentication;
import static com.jsan.jvaif.inf.constant.ResultEnum.success_login;

/**
 * @description: 身份验证相关controller
 * @author: jcwang
 * @create: 2019-08-03 21:27
 **/
@RestController
@Api("登录及身份验证相关")
@ApiModel(value = "Result", description = "通用返回对象")
public class LoginController {

    @Resource
    private IScyUserService scyUserService;

    @ApiOperation("登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userName",value = "用户名",required = true, defaultValue = "wangjc0801"),
        @ApiImplicitParam(name = "password", value = "密码",required = true, defaultValue = "wangjc0801")
    })
    @ApiResponses({
        @ApiResponse(code = 903,message = "身份验证失败")
    })
    @PostMapping(value = "/login")
    @SkipToken
    public Result login(
        @RequestParam(name = "userName")
            String userName,
        @RequestParam(name = "password")
            String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
        try {
            subject.login(usernamePasswordToken);
            return ResultUtil.success(success_login, subject.getPrincipal());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail(exception_authentication, null);
        }
    }

    @ApiOperation("更新token")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "token", required = true, value = "token值")
    })
    @GetMapping("/token")
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String newToken = scyUserService.refreshToken(token);
        return ResultUtil.success(newToken);
    }

}