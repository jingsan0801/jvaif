package com.jsan.jvaif.inf.service.impl;

import com.jsan.jvaif.inf.service.IScyAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description: 权限service impl
 * @author: jcwang
 * @create: 2019-08-11 16:19
 **/
@Slf4j
@Service
public class ScyAuthServiceImpl implements IScyAuthService {
    /**
     * 获取url和对应的权限配置
     * key: url
     * value: shiro自带的filter
     *
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getShiroFilterChain() {
        return null;
    }
}
