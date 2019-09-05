package com.jsan.jvaif.web.controller;

import com.jsan.jvaif.inf.constant.PublicConstant;
import com.jsan.jvaif.inf.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 通用controller
 * @author: jcwang
 * @create: 2019-09-01 19:58
 **/
public abstract class AbstractController {
    /**
     * 从request中获取token
     * @param request request
     * @return token值
     */
    String getUserName(HttpServletRequest request) {
        String authToken = request.getHeader(PublicConstant.REQUEST_AUTH_HEADER);
        return JwtUtil.getClaim(authToken);
    }
}
