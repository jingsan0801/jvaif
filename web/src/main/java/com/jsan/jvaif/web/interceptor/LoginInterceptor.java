package com.jsan.jvaif.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录校验
 * @author: jcwang
 * @create: 2019-07-30 20:38
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String token = request.getHeader("token");

        log.info("Object = {}",handler);
        if(StringUtils.isEmpty(token)) {
            log.error("login error");
            return false;
        }

        return true;
    }
}
