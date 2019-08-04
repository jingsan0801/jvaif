package com.jsan.jvaif.inf.domain.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description: shiro使用的Token实体
 * @author: jcwang
 * @create: 2019-08-03 00:47
 **/
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
