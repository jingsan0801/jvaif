package com.jsan.jvaif.tool.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 通用controller
 * @author: jcwang
 * @create: 2019-09-01 19:58
 **/
public abstract class AbstractController {
    public String getUserName(HttpServletRequest request) {
        return "";
    }
}
