package com.jsan.jvaif.inf.service.impl;

import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.service.IShiroRealmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: shiro realm 服务类
 * @author: jcwang
 * @create: 2019-08-21 16:54
 **/
@Slf4j
@Service
public class ShiroRealmServiceImpl implements IShiroRealmService {

    @Resource
    private IScyUserService scyUserService;

    /**
     * 返回权限对象
     *
     * @param userName
     * @return
     */
    @Override
    public AuthorizationInfo getAuthorizationInfo(String userName) {
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
}
