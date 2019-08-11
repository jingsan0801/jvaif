package com.jsan.jvaif.inf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.vo.Result;

/**
 *
 **/
public interface IScyUserService extends IService<ScyUser> {

    /**
     * 登录, 返回token
     * @param userName 用户名
     * @param password 密码
     * @return Result
     */
    String login(String userName, String password);

    /**
     * 判断用户名密码是否匹配
     * @param userName 用户名
     * @param password 密码
     * @return ScyUser
     */
    ScyUser checkUsernameAndPassword(String userName, String password);

    /**
     * 根据用户名获取用户实体类
     *
     * @param userName 用户名
     * @return scyUser
     */
    ScyUser getScyUserByName(String userName);

    /**
     * 新增一个用户实体
     *
     * @param userName 用户名
     * @param password 密码
     * @return Result
     */
    Result addUser(String userName, String password);

    /**
     * md5加密
     * (userName+salt)作为salt,md5加密两次
     *
     * @param text     原始密码
     * @param userName 用户名
     * @param salt     salt
     * @return (userName + salt)作为salt, md5加密两次得到的字符串
     */
    String shiroMd5(Object text, String userName, String salt);

    /**
     * 判断用户是否正常
     *
     * @param scyUser ScyUser
     * @return 是否正常
     */
    boolean checkUserStatus(ScyUser scyUser);

    /**
     * 判断token是否有效
     * @param token token
     * @return token是否有效
     */
    boolean checkToken(String token);

    /**
     * 更新token
     * @param token token
     * @return 新的token
     */
    String refreshToken(String token);
}
