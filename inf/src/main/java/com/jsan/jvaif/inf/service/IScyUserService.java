package com.jsan.jvaif.inf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsan.jvaif.inf.domain.ScyUser;
import org.springframework.stereotype.Component;

/**
 * @description: service
 * @author: Stone
 * @create: 2019-05-30 20:58
 **/
public interface IScyUserService extends IService<ScyUser> {

    /**
     * 根据用户名获取用户实体类
     * @param name 用户名
     * @return scyUser
     */
    ScyUser getScyUserByName(String name);
}
