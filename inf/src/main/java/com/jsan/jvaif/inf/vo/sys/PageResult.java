package com.jsan.jvaif.inf.vo.sys;

import lombok.Data;

import java.util.List;

/**
 * @description: 用于分页的返回实体
 * 字段名称与layui table.response 默认字段名称保持一致
 * @author: jcwang
 * @create: 2019-09-05 17:16
 **/

@Data
public class PageResult {
    private String code;
    private String msg;
    private long count;
    private List data;

    public PageResult() {}

}
