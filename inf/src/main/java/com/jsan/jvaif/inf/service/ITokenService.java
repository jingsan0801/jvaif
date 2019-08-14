package com.jsan.jvaif.inf.service;

/**
 * @description: Token service
 * @author: Stone
 * @create: 2019-08-14 21:08
 **/
public interface ITokenService {

    /**
     * 返回生成的token值, 同时将旧的token置为失效
     * @param key 生成token的key
     * @return 生成的token
     */
    String genToken(String ... key);

    /**
     * 返回当前有效的token
     * @param key key
     * @return 有效的token
     */
    String getToken(String key);

    /**
     * 判断该token是否有效
     * @param token token
     * @return 是否有效
     */
    boolean isValid(String token);

    /**
     * 将token置为失效
     * @param token token
     */
    void setInvalid(String token);
}
