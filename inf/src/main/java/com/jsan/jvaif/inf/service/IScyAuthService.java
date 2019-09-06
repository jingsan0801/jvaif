package com.jsan.jvaif.inf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.vo.ScyAuthVo;

import java.util.List;
import java.util.Map;

/**
 * @description: 权限service
 * @author: Stone
 * @create: 2019-08-06 21:32
 **/
public interface IScyAuthService extends IService<ScyAuth> {

    /**
     * 获取url和对应的权限配置
     * key: url
     * value: shiro自带的filter
     *
     * @return Map<String, String>
     */
    Map<String, String> getShiroFilterChain();

    /**
     * 根据vo的字段查询(不分页)
     *
     * @param vo 查询vo
     * @return
     */
    List<ScyAuth> getByVo(ScyAuthVo vo);

    /**
     * 根据vo的字段查询(分页)
     * @param vo 查询vo
     * @return
     */
    IPage<ScyAuth> getByVoForPage(ScyAuthVo vo);
}
