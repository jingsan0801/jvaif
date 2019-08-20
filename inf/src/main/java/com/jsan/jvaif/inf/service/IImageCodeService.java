package com.jsan.jvaif.inf.service;

import com.jsan.jvaif.inf.dto.ImageCodeDto;

/**
 * @description: 图形验证码service
 * @author: jcwang
 * @create: 2019-08-20 21:20
 **/
public interface IImageCodeService {

    /**
     * 生成图形验证码
     *
     * @return ImageCodeDto
     */
    ImageCodeDto generate();

    /**
     * 校验图形验证码输入是否正确
     *
     * @param code image code
     * @param uuid redis key
     * @return 是否正确
     */
    boolean check(String code, String uuid);
}
