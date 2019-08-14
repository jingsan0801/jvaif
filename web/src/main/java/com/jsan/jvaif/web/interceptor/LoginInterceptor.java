package com.jsan.jvaif.web.interceptor;

import com.jsan.jvaif.inf.constant.PublicConstant;
import com.jsan.jvaif.inf.exption.BusinessException;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.web.annotation.SkipAuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.jsan.jvaif.inf.constant.ResultEnum.exception_token_required;

/**
 * @description: 登录校验
 * 在filter之后触发
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
        String authToken = request.getHeader(PublicConstant.REQUEST_AUTH_HEADER);

        // 不是映射到方法,直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Method handlerMethod = ((HandlerMethod)handler).getMethod();
        // 检查是否要跳过token验证
        if (handlerMethod.isAnnotationPresent(SkipAuthToken.class)) {
            SkipAuthToken skipAuthToken = handlerMethod.getAnnotation(SkipAuthToken.class);
            if (skipAuthToken.required()) {
                return true;
            }
        }

        // 检查token是否为空
        if (StringUtils.isEmpty(authToken)) {
            throw new BusinessException(exception_token_required);
        }
        return true;
    }
}
