package com.jsan.jvaif.web.controller;

import com.jsan.jvaif.inf.constant.PublicConstant;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import com.jsan.jvaif.web.annotation.SkipAuthToken;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jsan.jvaif.inf.constant.ResultEnum.*;

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
        @ApiImplicitParam(name = "userName", value = "用户名", required = true, defaultValue = "wangjc0801"),
        @ApiImplicitParam(name = "password", value = "密码", required = true, defaultValue = "wangjc0801")})
    @ApiResponses({@ApiResponse(code = 903, message = "身份验证失败")})
    @PostMapping(value = "/login")
    @SkipAuthToken
    public Result login(
        @RequestParam(name = "userName")
            String userName,
        @RequestParam(name = "password")
            String password) {
        String authToken = scyUserService.login(userName, password);
        if (StringUtils.isEmpty(authToken)) {
            return ResultUtil.fail(exception_login);
        }
        return ResultUtil.success(success_login, authToken);
    }

    @ApiOperation("退出提示")
    @GetMapping(value = "/logout_msg")
    public Result logout() {
        scyUserService.logout();
        return ResultUtil.success(success_logout,true);
    }

    @ApiOperation("未登录提示")
    @RequestMapping(value = "/login_msg")
    @SkipAuthToken
    public Result login(HttpServletRequest request) {
        Result rs = (Result)request.getAttribute("rs");
        if (rs != null) {
            return rs;
        }
        return ResultUtil.fail(exception_login);
    }

    @ApiOperation("未授权提示")
    @GetMapping(value = "/unAuth")
    @SkipAuthToken
    public Result unAuth() {
        return ResultUtil.fail(exception_auth_deny);
    }

    @ApiOperation("更新auth_token")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = PublicConstant.REQUEST_AUTH_HEADER, required = true, value = "auth_token值")})
    @GetMapping("/auth_token")
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader(PublicConstant.REQUEST_AUTH_HEADER);
        String newToken = scyUserService.refreshToken(token);
        return ResultUtil.success(newToken);
    }

}