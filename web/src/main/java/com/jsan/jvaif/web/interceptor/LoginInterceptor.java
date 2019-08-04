package com.jsan.jvaif.web.interceptor;

import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录校验
 * @author: jcwang
 * @create: 2019-07-30 20:38
 **/
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private IScyUserService scyUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException("token is required");
        }

        String userName = JwtUtil.getUserName(token);
        if (StringUtils.isEmpty(userName)) {
            log.info("解析token失败:{}", token);
            throw new AuthenticationException("解析token失败");
        }

        if (!scyUserService.isValid(userName)) {
            log.info("该用户不存在或状态不正常:{}", userName);
            throw new AuthenticationException("该用户不存在或状态不正常");
        }

        // 验证token有效性
        ScyUser scyUser = scyUserService.getScyUserByName(userName);
        if (!JwtUtil.verify(token, scyUser.getName(), scyUser.getPassword())) {
            log.error("token[{}]无效: {}", token, scyUser);
            throw new AuthenticationException("token无效");
        }

        return true;
    }
}
