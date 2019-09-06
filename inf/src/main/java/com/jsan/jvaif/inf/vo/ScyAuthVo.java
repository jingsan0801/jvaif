package com.jsan.jvaif.inf.vo;

import com.jsan.jvaif.inf.vo.sys.Pageable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-09-04 16:40
 **/
@ApiModel(value = "ScyAuthVo", description = "权限vo")
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ScyAuthVo extends Pageable {

    /**
     * 权限pk
     */
    private String id;

    /**
     * 权限名称
     */
    private String authName;
}
