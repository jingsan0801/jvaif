package com.jsan.jvaif.inf.domain.shiro;

import com.jsan.jvaif.inf.domain.JwtToken;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.exption.BusinessException;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jsan.jvaif.inf.constant.ResultEnum.exception_token_required;

/**
 * @description: shiro使用的Realm
 * @author: jcwang
 * @create: 2019-08-03 00:50
 **/
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private IScyUserService scyUserService;

    /**
     * @param token token
     * @return 是否是指定类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
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
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Set<String> roleCodeSet = scyUserService.getRoleSetByUserName(userName).stream().map(ScyRole::getRoleCode)
            .collect(Collectors.toSet());
        Set<String> authExpSet =
            scyUserService.getAuthSetByUserName(userName).stream().map(ScyAuth::getAuthExp).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleCodeSet);
        simpleAuthorizationInfo.setStringPermissions(authExpSet);

        log.info("from doGetAuthorizationInfo: {}, {}, {}", userName, roleCodeSet, authExpSet);

        return simpleAuthorizationInfo;
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
     * Clears out the AuthorizationInfo cache entry for the specified account.
     * <p/>
     * This method is provided as a convenience to subclasses so they can invalidate a cache entry when they
     * change an account's authorization data (add/remove roles or permissions) during runtime.  Because an account's
     * AuthorizationInfo can be cached, there needs to be a way to invalidate the cache for only that account so that
     * subsequent authorization operations don't used the (old) cached value if account data changes.
     * <p/>
     * After this method is called, the next authorization check for that same account will result in a call to
     * {@link #getAuthorizationInfo(PrincipalCollection) getAuthorizationInfo}, and the
     * resulting return value will be cached before being returned so it can be reused for later authorization checks.
     * <p/>
     * If you wish to clear out all associated cached data (and not just authorization data), use the
     * {@link #clearCache(PrincipalCollection)} method instead (which will in turn call this
     * method by default).
     *
     * @param principals the principals of the account for which to clear the cached AuthorizationInfo.
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
}
