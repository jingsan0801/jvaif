package com.jsan.jvaif.web.interceptor;

import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.web.annotation.SkipToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

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

        // 不是映射到方法,直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Method handlerMethod = ((HandlerMethod)handler).getMethod();
        // 检查是否要跳过token验证
        if (handlerMethod.isAnnotationPresent(SkipToken.class)) {
            SkipToken skipToken = handlerMethod.getAnnotation(SkipToken.class);
            if (skipToken.required()) {
                return true;
            }
        }
        return scyUserService.checkToken(token);
    }
}
