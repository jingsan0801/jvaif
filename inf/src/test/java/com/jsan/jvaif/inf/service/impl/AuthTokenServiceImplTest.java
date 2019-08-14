package com.jsan.jvaif.inf.service.impl;

import com.jsan.jvaif.inf.InfApplicationTests;
import com.jsan.jvaif.inf.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static com.jsan.jvaif.inf.constant.PublicConstant.REDIS_KEY_TOKEN;

/**
 * AuthTokenServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 14, 2019</pre>
 */
@Slf4j
public class AuthTokenServiceImplTest extends InfApplicationTests {

    @Resource(name = "authTokenServiceImpl")
    private ITokenService tokenService;

    private String userName = "wangjc0801";

    /**
     * Method: genToken(String... keys)
     */
    @Test
    public void testGenToken() throws Exception {
        log.info("return : {}",tokenService.genToken(userName,"123"));
        log.info("get key[{}] value from redis: {}",REDIS_KEY_TOKEN + userName,tokenService.getToken(userName));
    }

    /**
     * Method: getToken(String key)
     */
    @Test
    public void testGetToken() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isValid(String token)
     */
    @Test
    public void testIsValid() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setInvalid(String token)
     */
    @Test
    public void testSetInvalid() throws Exception {
        //TODO: Test goes here...
    }

} 
