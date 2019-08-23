package com.jsan.jvaif.inf.domain.shiro;

import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.service.IShiroRealmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @description: 给页面登录使用的realm
 * @author: jcwang
 * @create: 2019-08-21 16:36
 **/
@Slf4j
public class SysRealm extends AuthorizingRealm {

    @Resource
    private IShiroRealmService shiroRealmService;

    @Resource
    private IScyUserService scyUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String)principals.getPrimaryPrincipal();
        return shiroRealmService.getAuthorizationInfo(userName);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        String userName = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        ScyUser scyUser = scyUserService.checkUsernameAndPassword(userName, password);
        if (scyUser != null) {
            return new SimpleAuthenticationInfo(userName, password, getName());
        }
        return null;
    }
}
