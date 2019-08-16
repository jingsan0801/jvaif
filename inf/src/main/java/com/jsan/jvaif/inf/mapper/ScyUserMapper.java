package com.jsan.jvaif.inf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.domain.ScyUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @description: 用户mapper
 * @author: Stone
 * @create: 2019-05-30 00:43
 **/
@Component
public interface ScyUserMapper extends BaseMapper<ScyUser> {
    /**
     * 获取该用户的所属角色
     *
     * @param userName 用户名
     * @return 改用户的所属角色
     */
    Set<ScyRole> getRoleSetByUsername(
        @Param("userName")
            String userName);

    /**
     * 按用户名获取权限集合
     *
     * @param userName 用户名
     * @return 该用户的所有权限信息
     */
    Set<ScyAuth> getAuthSetByUserName(String userName);
}
