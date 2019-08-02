package com.jsan.jvaif.inf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.mapper.ScyUserMapper;
import com.jsan.jvaif.inf.service.IScyUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-08-01 21:58
 **/
@Service
public class ScyUserServiceImpl extends ServiceImpl<ScyUserMapper, ScyUser> implements IScyUserService {

    @Resource
    private ScyUserMapper scyUserMapper;

    /**
     * 根据用户名获取用户实体类
     *
     * @param name 用户名
     * @return scyUser
     */
    @Override
    public ScyUser getScyUserByName(String name) {
        return scyUserMapper.selectOne(new QueryWrapper<ScyUser>().eq("name", name));
    }
}
