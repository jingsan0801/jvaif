package com.jsan.jvaif.inf.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表 
 * </p>
 *
 * @author wangjc
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_scy_resource")
public class ScyResource implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源url
     */
    private String resUrl;

    /**
     * 父级id
     */
    private String parentResId;

    /**
     * 排序id
     */
    private Integer orderId;

    /**
     * 资源类型;1:菜单;2:按钮;3.数据
     */
    private String resType;

    /**
     * 资源图片
     */
    private String resPic;

    /**
     * 资源描述
     */
    private String resDesc;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String note;

    /**
     * 树形路径
     */
    private String resPath;

    /**
     * 是否叶子节点
     */
    private String isLeaf;


}
