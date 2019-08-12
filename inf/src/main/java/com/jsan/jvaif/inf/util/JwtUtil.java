package com.jsan.jvaif.inf.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @description: jwt工具类
 * @author: jcwang
 * @create: 2019-08-02 23:06
 **/
@Slf4j
public class JwtUtil {

    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 10 * 60 * 1000;

    private static final String CLAIM = "userName";

    /**
     * 校验token是否正确
     *
     * @param token    token
     * @param userName 待校验的userName
     * @param secret   待校验的password
     * @return 是否正确
     */
    public static boolean verify(String token, String userName, String secret) {
        try {
            // 把用户加密后的密码字符串作为secret
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim(CLAIM, userName).build();
            DecodedJWT decodedJwt = jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据token获取userName
     *
     * @param token token
     * @return token中包含的userName
     */
    public static String getClaim(String token) {
        try {
            DecodedJWT decodedJwt = JWT.decode(token);
            return decodedJwt.getClaim(CLAIM).asString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成token
     *
     * @param userName 待加密的userName
     * @param secret   待加密的password
     * @return jwt使用的token
     */
    public static String sign(String userName, String secret) {
        try {
            Date expiresDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withClaim(CLAIM, userName).withExpiresAt(expiresDate).sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }
}
