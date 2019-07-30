package com.jsan.jvaif.inf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.mapper.ScyUserMapper;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.vo.Result;
import org.springframework.stereotype.Service;

/**
 * @description: service impl
 * @author: Stone
 * @create: 2019-05-30 21:00
 **/
@Service
public class ScyUserServiceImpl extends ServiceImpl<ScyUserMapper, ScyUser> implements IScyUserService {

    /**
     * @return Result
     */
    @Override
    public Result getListByPage() {
        return null;
    }
}
