package com.jsan.jvaif.inf.service;

import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @description: shiro realm 服务类
 * @author: jcwang
 * @create: 2019-08-21 16:52
 **/
public interface IShiroRealmService {

    /**
     * 返回权限对象
     *
     * @param userName
     * @return
     */
    AuthorizationInfo getAuthorizationInfo(String userName);
}
