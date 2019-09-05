package com.jsan.jvaif.inf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.mapper.ScyAuthMapper;
import com.jsan.jvaif.inf.service.IScyAuthService;
import com.jsan.jvaif.inf.vo.ScyAuthVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 权限service impl
 * @author: jcwang
 * @create: 2019-08-11 16:19
 **/
@Slf4j
@Service
public class ScyAuthServiceImpl extends ServiceImpl<ScyAuthMapper, ScyAuth> implements IScyAuthService {

    @Resource
    private ScyAuthMapper scyAuthMapper;

    /**
     * 获取url和对应的权限配置
     * key: url
     * value: shiro自带的filter
     *
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getShiroFilterChain() {
        Map<String, String> rsMap = new HashMap<>();
        return rsMap;
    }

    @Override
    public List<ScyAuth> getByVo(ScyAuthVo vo) {
        QueryWrapper<ScyAuth> qw = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getId())) {
            qw.eq("id", vo.getId());
        }
        if (!StringUtils.isEmpty(vo.getAuthName())) {
            qw.like("auth_name", vo.getAuthName());
        }
        return scyAuthMapper.selectList(qw);
    }
}
