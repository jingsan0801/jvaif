package com.jsan.jvaif.inf.vo;

import lombok.Data;

/**
 * @description: 返回实体类
 * @author: Stone
 * @create: 2019-05-30 22:16
 **/

@Data
public class Result {

    private String code;
    private String msg;
    private Boolean success;
    private Object data;
}
