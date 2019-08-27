package com.jsan.jvaif.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @description: httpLog切面
 * @author: Stone
 * @create: 2018-08-09 11:18
 **/
@Aspect
@Component
@Slf4j
public class HttpLogAspect {
    @Pointcut("@annotation(com.jsan.jvaif.web.annotation.HttpLog)")
    public void pointCut() {

    }

    /**
     * 之前触发
     *
     * @param joinPoint
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 记录request请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("[reqMethod={}, args={}]",
            joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
            joinPoint.getArgs());
        log.info("[reqUrl = {}, method={}, ip={}]", request.getRequestURI(), request.getMethod(),
            request.getRemoteAddr());

    }

    /**
     * 之后返回触发, 在after之后
     *
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "pointCut()")
    public void after(Object result) {
        log.info("[afterReturning resp={}]", result);
    }

}
