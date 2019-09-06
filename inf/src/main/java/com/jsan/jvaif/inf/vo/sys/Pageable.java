package com.jsan.jvaif.inf.vo.sys;

import lombok.Data;

/**
 * @description: 分页实体类
 * 属性名称与layui的table.request的默认属性保持一致
 * @author: Stone
 * @create: 2019-09-05 17:44
 **/
@Data
public class Pageable {
    /**
     * 每页数量
     */
    private int limit = 20;
    /**
     * 当前页码
     */
    private int page = 1;
}
