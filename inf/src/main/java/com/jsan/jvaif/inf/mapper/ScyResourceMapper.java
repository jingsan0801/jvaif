package com.jsan.jvaif.inf.mapper;

import com.jsan.jvaif.inf.domain.ScyResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.dto.ScyResourceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表  Mapper 接口
 * </p>
 *
 * @author wangjc
 * @since 2019-08-30
 */
public interface ScyResourceMapper extends BaseMapper<ScyResource> {

    List<ScyResourceDTO> getFirstLevelMenuByRole(@Param(value = "roleSet") Set<ScyRole> roleSet);

    List<ScyResourceDTO> getSubsByResPathAndParentId(@Param(value = "resPath")String resPath, @Param(value = "parentResId")String parentResId);

}
