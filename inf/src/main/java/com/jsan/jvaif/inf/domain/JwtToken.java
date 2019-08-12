package com.jsan.jvaif.inf.domain;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-08-12 10:48
 **/
public class JwtToken implements AuthenticationToken {

    private String authToken;

    public JwtToken(String authToken) {
        this.authToken = authToken;
    }


    @Override
    public Object getPrincipal() {
        return authToken;
    }


    @Override
    public Object getCredentials() {
        return authToken;
    }
}
