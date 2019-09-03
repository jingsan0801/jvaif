package com.jsan.jvaif.inf.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.jsan.jvaif.common.util.MathUtil;
import com.jsan.jvaif.inf.dto.ImageCodeDTO;
import com.jsan.jvaif.inf.exption.BusinessException;
import com.jsan.jvaif.inf.service.IImageCodeService;
import com.jsan.jvaif.inf.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.jsan.jvaif.inf.constant.PublicConstant.IMAGE_CODE_EXPIRE_SECONDS;
import static com.jsan.jvaif.inf.constant.PublicConstant.REDIS_KEY_IMAGE_CODE;
import static com.jsan.jvaif.inf.constant.ResultEnum.exception_image_code_generator;

/**
 * @description: 图形验证码service impl
 * @author: jcwang
 * @create: 2019-08-20 21:26
 **/
@Service
public class ImageCodeServiceImpl implements IImageCodeService {

    @Resource
    private RedisService redisService;

    @Resource
    private DefaultKaptcha defaultKaptcha;

    /**
     * 生成图形验证码
     *
     * @return ImageCodeDto
     */
    @Override
    public ImageCodeDTO generate() {
        String redisKey = MathUtil.getUuid();
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            redisService.set(REDIS_KEY_IMAGE_CODE + redisKey, createText, IMAGE_CODE_EXPIRE_SECONDS);

            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);

        } catch (IllegalArgumentException | IOException e) {
            throw new BusinessException(exception_image_code_generator);
        }

        byte[] b = jpegOutputStream.toByteArray();
        String imageString = "data:image/JPEG;base64," + Base64Utils.encodeToString(b);

        ImageCodeDTO imageCodeDto = new ImageCodeDTO();
        imageCodeDto.setExpireSeconds(IMAGE_CODE_EXPIRE_SECONDS);
        imageCodeDto.setImageCode(imageString);
        imageCodeDto.setUuid(redisKey);

        return imageCodeDto;

    }

    /**
     * 校验图形验证码输入是否正确
     *
     * @param code image code
     * @param uuid redis key
     * @return 是否正确
     */
    @Override
    public boolean check(String code, String uuid) {
        String codeFromRedis = redisService.get(REDIS_KEY_IMAGE_CODE + uuid);
        if (!StringUtils.isEmpty(codeFromRedis)) {
            return codeFromRedis.equalsIgnoreCase(code);
        }
        return false;
    }
}
