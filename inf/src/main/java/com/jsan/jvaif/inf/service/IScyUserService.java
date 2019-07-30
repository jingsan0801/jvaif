package com.jsan.jvaif.inf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.vo.Result;

/**
 * @description: service
 * @author: Stone
 * @create: 2019-05-30 20:58
 **/
public interface IScyUserService extends IService<ScyUser> {

    /**
     *
     * @return Result
     */
    Result getListByPage();
}
