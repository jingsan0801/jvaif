package com.jsan.jvaif.inf.domain;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 * jwt用的token类, api接口使用
 * @description:
 * @author: jcwang
 * @create: 2019-08-12 10:48
 **/
public class ApiToken implements AuthenticationToken {

    private String authToken;

    public ApiToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return 返回
     */
    @Override
    public Object getPrincipal() {
        return authToken;
    }


    @Override
    public Object getCredentials() {
        return authToken;
    }
}
