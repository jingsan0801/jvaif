package com.jsan.jvaif.inf.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 权限
 * @author: Stone
 * @create: 2019-05-30 00:40
 **/
@TableName("t_scy_auth")
@Data
public class ScyAuth implements Serializable, Cloneable {
    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 权限名称
     */
    private String authName;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * 权限表达式
     */
    private String authExp;
    /**
     * 权限简介
     */
    private String authDesc;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
}