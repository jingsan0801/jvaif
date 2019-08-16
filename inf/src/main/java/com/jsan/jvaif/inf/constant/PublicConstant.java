package com.jsan.jvaif.inf.constant;

/**
 * @description: 公共常量
 * @author: jcwang
 * @create: 2019-08-05 00:26
 **/
public interface PublicConstant {

    int COMMON_VALID = 1;
    int COMMON_INVALID = 0;

    /**
     * request请求header属性名称
     */
    String REQUEST_AUTH_HEADER = "auth_token";

    /**
     * redis key name
     */
    String REDIS_KEY_TOKEN = "auth_token_";

    /**
     * jwt token过期时间, 单位毫秒;默认12小时
     */
    long TOKEN_EXPIRE_TIME = 12 * 60 * 60 * 1000;
}
