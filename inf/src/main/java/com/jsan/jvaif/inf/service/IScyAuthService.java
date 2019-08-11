package com.jsan.jvaif.inf.service;

import java.util.Map;

/**
 * @description: 权限service
 * @author: Stone
 * @create: 2019-08-06 21:32
 **/
public interface IScyAuthService {

    /**
     * 获取url和对应的权限配置
     * key: url
     * value: shiro自带的filter
     *
     * @return Map<String, String>
     */
    Map<String, String> getShiroFilterChain();
}
