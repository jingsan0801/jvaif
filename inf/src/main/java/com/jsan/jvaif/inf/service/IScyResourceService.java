package com.jsan.jvaif.inf.service;

import com.jsan.jvaif.inf.domain.ScyResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.vo.ScyResourceVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表  服务类
 * </p>
 *
 * @author wangjc
 * @since 2019-08-30
 */
public interface IScyResourceService extends IService<ScyResource> {

    List<ScyResourceVo> getAllMenuByRole(Set<ScyRole> roleSet);

}
