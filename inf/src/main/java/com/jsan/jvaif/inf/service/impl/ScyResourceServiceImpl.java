package com.jsan.jvaif.inf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.jvaif.inf.constant.DicItemEnum;
import com.jsan.jvaif.inf.domain.ScyResource;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.vo.ScyResourceVo;
import com.jsan.jvaif.inf.mapper.ScyResourceMapper;
import com.jsan.jvaif.inf.service.IScyResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表  服务实现类
 * </p>
 *
 * @author wangjc
 * @since 2019-08-30
 */
@Service
@Slf4j
public class ScyResourceServiceImpl extends ServiceImpl<ScyResourceMapper, ScyResource> implements IScyResourceService {

    @Resource
    private ScyResourceMapper scyResourceMapper;

    @Override
    public List<ScyResourceVo> getAllMenuByRole(Set<ScyRole> roleSet) {
        List<ScyResourceVo> rs = new ArrayList<>();
        // 获取第一层菜单
        List<ScyResourceVo> firstLevelResVo = scyResourceMapper.getFirstLevelMenuByRole(roleSet);
        for (ScyResourceVo resourceVo : firstLevelResVo) {
            initSubsByResPathAndParentId(resourceVo);
            rs.add(resourceVo);
        }
        return rs;
    }

    /**
     * 递归获取所有的层级的子数据
     *
     * @param scyResourceVo 父级对象
     */
    private void initSubsByResPathAndParentId(ScyResourceVo scyResourceVo) {
        List<ScyResourceVo> subs =
            scyResourceMapper.getSubsByResPathAndParentId(scyResourceVo.getResPath(), scyResourceVo.getId());
        for (ScyResourceVo sub : subs) {
            if (!DicItemEnum.yes.getKey().equals(sub.getIsLeaf())) {
                initSubsByResPathAndParentId(sub);
            }
        }
        scyResourceVo.setList(subs);
    }

}
