package com.jsan.jvaif.inf.vo;

import lombok.Data;

/**
 * @description: 图形验证码实体类
 * @author: jcwang
 * @create: 2019-08-20 21:24
 **/
@Data
public class ImageCodeVo {
    private long expireSeconds;
    private String imageCode;
    private String uuid;
}
