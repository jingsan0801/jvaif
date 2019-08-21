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
     * jwt token过期时间, 单位小时
     */
    long TOKEN_EXPIRE_HOURS = 12;

    /**
     * shiro remember me功能, 生成cookie的有效期, 单位: 天
     */
    int REMEMBER_ME_COOKIE_EXPIRE_DAY = 30;

    /**
     * shiro remember me功能, 生成cookie的名称
     */
    String REMEMBER_ME_COOKIE_NAME = "rememberMe";

    /**
     * shiro remember me功能, 生成cookie的加密密钥, 要求16位
     */
    String REMEMBER_ME_COOKIE_KEY = "doYouRememberMe!";

    /**
     * 图形验证码过期时间
     */
    long IMAGE_CODE_EXPIRE_SECONDS = 60;

    // *********** redis key name **************

    /**
     * REDIS_KEY_TOKEN + userName 存放用户对应的auth_token
     */
    String REDIS_KEY_TOKEN = "auth_token_";

    /**
     * REDIS_KEY_IMAGE_CODE + 随机数: 存放图形验证码内容, 做校验用
     */
    String REDIS_KEY_IMAGE_CODE = "image_code_";
}
