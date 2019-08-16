package com.jsan.jvaif.inf.service.impl;

import com.jsan.jvaif.inf.service.ITokenService;
import com.jsan.jvaif.inf.service.RedisService;
import com.jsan.jvaif.inf.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import static com.jsan.jvaif.inf.constant.PublicConstant.REDIS_KEY_TOKEN;
import static com.jsan.jvaif.inf.constant.PublicConstant.TOKEN_EXPIRE_TIME;

/**
 * @description: 登录token service实现类
 * @author: jcwang
 * @create: 2019-08-14 21:13
 **/
@Service("authTokenServiceImpl")
public class AuthTokenServiceImpl implements ITokenService {

    @Resource
    private RedisService redisService;

    /**
     * 返回生成的token值, 同时将旧的token置为失效
     * 使用username和加密后的password计算
     *
     * @param keys username/password
     * @return 生成的token
     */
    @Override
    public String genToken(String... keys) {
        String userName = keys[0];
        String password = keys[1];
        String authToken = JwtUtil.sign(userName, password);
        redisService.set(REDIS_KEY_TOKEN + userName, authToken, TOKEN_EXPIRE_TIME / 1000);
        return authToken;
    }

    /**
     * 返回当前有效的token
     *
     * @param key key
     * @return 有效的token
     */
    @Override
    public String getToken(String key) {
        return redisService.get(REDIS_KEY_TOKEN + key);
    }

    /**
     * 判断该token是否有效
     *
     * @param token token
     * @return 是否有效
     */
    @Override
    public boolean isValid(String token) {
        String userName = JwtUtil.getClaim(token);
        String redisToken = redisService.get(REDIS_KEY_TOKEN + userName);
        if ((!StringUtils.isEmpty(token)) && (!StringUtils.isEmpty(token))) {
            return token.equals(redisToken);
        }
        return false;
    }

    /**
     * 将token置为失效
     *
     * @param token token
     */
    @Override
    public void setInvalid(String token) {
        String userName = JwtUtil.getClaim(token);
        redisService.delete(REDIS_KEY_TOKEN + userName);
    }
}
