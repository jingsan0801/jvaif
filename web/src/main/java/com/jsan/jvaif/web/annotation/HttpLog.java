package com.jsan.jvaif.web.annotation;

import java.lang.annotation.*;

/**
 * @description: 记录http请求日志
 * @author: Stone
 * @create: 2018-08-09 11:14
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpLog {
    String value() default "" ;
}
