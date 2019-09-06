package com.jsan.jvaif.web.aspect;

import com.jsan.jvaif.web.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-09-06 17:44
 **/
@Aspect
@Component
@Slf4j
public class OperationLogAspect {
    @Pointcut("@annotation(com.jsan.jvaif.web.annotation.OperationLog)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object doBefore(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Map<String,Object> annotationParams = getParamFromAnnotation(point);
            log.info("getParamFromAnnotation: {}",annotationParams);
            log.info("IP = {},port = {}",request.getRemoteAddr(),request.getRemotePort());
        }

        long start = System.currentTimeMillis();
        Object rs = point.proceed();
        long duration = System.currentTimeMillis() - start;


        Object[] arguments = point.getArgs();
        log.info("input = {}, output = {}",arguments,rs);
        log.info("duration = {} ms" , duration);
        //todo: 把日志字段入库
        return rs;
    }

    private Map<String, Object> getParamFromAnnotation(ProceedingJoinPoint point) throws ClassNotFoundException {
        String targetName = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] arguments = point.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();

        Map<String, Object> annotationParams = new HashMap<>(16);

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] classes = method.getParameterTypes();
                if (classes.length == arguments.length) {
                    annotationParams.put("module", method.getAnnotation(OperationLog.class).module());
                    annotationParams.put("action", method.getAnnotation(OperationLog.class).action());
                    annotationParams.put("actionDesc", method.getAnnotation(OperationLog.class).actionDesc());
                    annotationParams.put("note", method.getAnnotation(OperationLog.class).note());
                    break;
                }
            }
        }
        return annotationParams;
    }
}
