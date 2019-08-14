package com.jsan.jvaif.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 不做token校验
 * @author: Stone
 * @create: 2019-08-05 02:48
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipAuthToken {
    boolean required() default true;
}
