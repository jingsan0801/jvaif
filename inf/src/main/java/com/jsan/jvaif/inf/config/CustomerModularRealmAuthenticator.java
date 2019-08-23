package com.jsan.jvaif.inf.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @description: 自定义认证器
 * @author: jcwang
 * @create: 2019-08-21 18:09
 **/
@Slf4j
public class CustomerModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        log.info("UserModularRealmAuthenticator:method doAuthenticate() execute ");

        Collection<Realm> realms = getRealms();

        if (realms.size() == 1) {
            return doSingleRealmAuthentication(realms.iterator().next(), authenticationToken);
        } else {
            return doMultiRealmAuthentication(realms, authenticationToken);
        }
        // 强制转换回自定义的CustomizedToken
       /* UserToken userToken = (UserToken) authenticationToken;
        // 登录类型
        String loginType = userToken.getLoginType()
        // 所有Realm

        // 登录类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>()
        for (Realm realm : realms) {
            if (realm.getName().contains(loginType))
            typeRealms.add(realm)
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            logger.info("doSingleRealmAuthentication() execute ")
            return doSingleRealmAuthentication(typeRealms?.get(0), userToken)
        }
        else{
            logger.info("doMultiRealmAuthentication() execute ")
            return doMultiRealmAuthentication(typeRealms, userToken)
        }*/
    }
}
