package com.jsan.jvaif.web.aspect;

import com.jsan.jvaif.inf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @description: 结果字段过滤,
 * 需要满足一下条件:
 * 1. controller方法的第一个参数要是request
 * 2. 只对@GetMapping的方法起作用
 * 3. controller的mapping: xxx/{xxx}/**
 * 4. 访问时的url为: xxx/{xxx}/id,name, 其中id,name必须为Result中data中的实体类的字段名称
 * @author: Stone
 * @create: 2018-08-09 12:54
 **/
@Aspect
@Component
@Slf4j
public class ResultMapAspect {
    @Pointcut("execution(* com.jsan.jvaif.web.controller..*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        // 获取被切函数 参数
        Object[] args = pjp.getArgs();
        // 获取被切函数的 返回值
        Object returnObj = pjp.proceed();
        if (args == null || args.length == 0 || args[0] == null || !(args[0] instanceof HttpServletRequest)) {
            return returnObj;
        }
        HttpServletRequest request = (HttpServletRequest)args[0];
        String columnStr = extractPathFromPattern(request);
        // 如果没有列名
        if (StringUtils.isEmpty(columnStr)) {
            return returnObj;
        }
        String[] columns = columnStr.split(",");

        // 只有返回结果为Result的才会起作用
        if (returnObj instanceof Result) {
            // 转化为map
            Result resultVo = (Result)returnObj;
            // 约定data为数据key
            Object data = resultVo.getData();
            if (data != null) {
                // 如果是多条数据
                if (data instanceof List) {
                    List<Object> dataList = (List<Object>)data;
                    // 装新的结果list<map>
                    List<Map<String, Object>> dataMapList = new ArrayList<Map<String, Object>>();
                    for (Object d : dataList) {
                        // 装新的结果map
                        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
                        fetchValue(columns, d, dataMap);
                        dataMapList.add(dataMap);
                    }
                    resultVo.setData(dataMapList);
                } else if (!(data instanceof Map) && !(data instanceof Set)) {
                    // 如果是单条数据,不是map也不是set
                    // 装新的结果map
                    Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
                    fetchValue(columns, data, dataMap);
                    resultVo.setData(dataMap);
                }
            }
        }
        log.info(columnStr);
        return returnObj;
    }

    /**
     * 取出变量值
     *
     * @param columns 需要取出的变量名
     * @param object  取出对象
     * @param dataMap 目标map
     */
    private void fetchValue(String[] columns, Object object, Map<String, Object> dataMap) {
        for (String col : columns) {
            Field f = ReflectionUtils.findField(object.getClass(), col);
            if(f == null) {
                continue;
            }
            f.setAccessible(true);
            Object o = ReflectionUtils.getField(f, object);
            dataMap.put(col, o);
        }
    }

    /**
     * 拿出mapping为/{xx}的值
     *
     * @param request request
     * @return
     */
    @SuppressWarnings("unused")
    private Map<String, String> extractPathValueFromPattern(final HttpServletRequest request) {
        String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractUriTemplateVariables(bestMatchPattern, path);
    }

    /**
     * 拿出mapping为/{xx}/**的值
     *
     * @param request request
     * @return
     */
    private String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }
}
