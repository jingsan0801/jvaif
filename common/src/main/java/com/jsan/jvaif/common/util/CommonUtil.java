package com.jsan.jvaif.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @description: 通用工具类
 * @author: jcwang
 * @create: 2019-08-03 00:13
 **/

public class CommonUtil {
    /**
     * 获取异常堆栈信息
     * @param ex 异常类
     * @return 异常堆栈信息
     */
    public static String getStackTraceInfoOfException(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
