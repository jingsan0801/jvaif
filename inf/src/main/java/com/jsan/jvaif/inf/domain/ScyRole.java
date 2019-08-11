package com.jsan.jvaif.inf.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @description: 角色
 * @author: jcwang
 * @create: 2019-08-07 14:16
 **/
@Data
@TableName("t_scy_role")
public class ScyRole {
    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色简介
     */
    private String roleDesc;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 状态
     */
    private String status;
}
