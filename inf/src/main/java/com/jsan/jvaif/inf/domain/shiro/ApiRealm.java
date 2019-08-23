package com.jsan.jvaif.inf.domain.shiro;

import com.jsan.jvaif.inf.domain.ApiToken;
import com.jsan.jvaif.inf.exption.BusinessException;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.service.IShiroRealmService;
import com.jsan.jvaif.inf.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import static com.jsan.jvaif.inf.constant.ResultEnum.exception_token_required;

/**
 * @description: shiro使用的Realm
 * @author: jcwang
 * @create: 2019-08-03 00:50
 **/
@Slf4j
public class ApiRealm extends AuthorizingRealm {

    @Resource
    private IShiroRealmService shiroRealmService;

    @Resource
    private IScyUserService scyUserService;

    /**
     * @param token token
     * @return 是否是指定类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ApiToken;
    }

    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String authToken = (String)principalCollection.getPrimaryPrincipal();
        String userName = JwtUtil.getClaim(authToken);
        return shiroRealmService.getAuthorizationInfo(userName);
    }

    /**
     * 身份验证
     * Authentication 是用来验证用户身份，subject.login()触发
     *
     * @param authenticationToken authenticationToken
     * @return AuthenticationInfo
     * @throws
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        String authToken = (String)authenticationToken.getPrincipal();

        if (StringUtils.isEmpty(authToken)) {
            throw new BusinessException(exception_token_required);
        }

        scyUserService.checkToken(authToken);
        return new SimpleAuthenticationInfo(authToken, authToken, getName());
    }

    /**
     * 清空缓存的权限数据
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
}
