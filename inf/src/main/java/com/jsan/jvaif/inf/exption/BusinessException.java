package com.jsan.jvaif.inf.exption;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsan.jvaif.common.util.JsonUtil;
import com.jsan.jvaif.inf.constant.ResultEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 系统自定义异常
 * @author: jcwang
 * @create: 2019-08-04 23:17
 **/
public class BusinessException extends RuntimeException {
    /**
     * 用于定位异常的附带信息
     */
    @Getter
    private Object addition;

    /**
     * 用于构建异常的枚举
     */
    @Getter
    private ResultEnum resultEnum;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }

    public BusinessException(ResultEnum resultEnum, Object data) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
        this.addition = data;
    }

    /**
     * exception的json对象
     *
     * @return JSONObject
     */
    public JSONObject getAdditionForJson() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("addition", JSON.toJSON(addition));
        return JsonUtil.mapToJsonObject(map);
    }
}
