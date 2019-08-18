package com.jsan.jvaif.tool.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表 
 * </p>
 *
 * @author wangjc
 * @since 2019-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_scy_auth")
public class ScyAuth implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 权限名称
     */
    private String authName;

    /**
     * 资源id
     */
    private String resId;

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
    private LocalDateTime createdTime;


}
