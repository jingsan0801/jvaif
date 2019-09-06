package com.jsan.jvaif.web.annotation;

import java.lang.annotation.*;

/**
 * @description: 记录业务日志
 * @author: Stone
 * @create: 2019-09-06 17:22
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    /**
     * 操作模块
     *
     * @return
     */
    String module() default "";

    /**
     * 操作动作
     *
     * @return
     */
    String action() default "";

    /**
     * 操作描述
     *
     * @return
     */
    String actionDesc() default "";

    /**
     * 备注
     *
     * @return
     */
    String note() default "";

}
