package com.jsan.jvaif.inf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.vo.Result;
import org.springframework.stereotype.Component;

/**
 **/
public interface IScyUserService extends IService<ScyUser> {

    /**
     * 根据用户名获取用户实体类
     * @param userName 用户名
     * @return scyUser
     */
    ScyUser getScyUserByName(String userName);

    /**
     * 新增一个用户实体
     * @param userName  用户名
     * @param password  密码
     * @return Result
     */
    Result addUser(String userName, String password);

    /**
     * md5加密
     * (userName+salt)作为salt,md5加密两次
     * @param text      原始密码
     * @param userName  用户名
     * @param salt      salt
     * @return (userName+salt)作为salt,md5加密两次得到的字符串
     */
    String shiroMd5(Object text, String userName, String salt);

    /**
     * 判断用户是否正常
     * @param userName 用户名
     * @return 是否正常
     */
    boolean isValid(String userName);
}
