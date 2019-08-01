package com.jsan.jvaif.inf.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.vo.Result;

/**
 * @description: 返回Result的工具类
 * @author: jcwang
 * @create: 2019-08-01 21:29
 **/
public class ResultUtil {
    public static Result success() {
        Result result = new Result();
        result.setSuccess(ResultEnum.pub_success.isSuccessFlag());
        result.setMsg(ResultEnum.pub_success.getMsg());
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setSuccess(ResultEnum.pub_fail.isSuccessFlag());
        result.setMsg(ResultEnum.pub_fail.getMsg());
        return result;
    }

    public static Result success(Object object) {
        Result result = new Result();
        result.setSuccess(ResultEnum.pub_success.isSuccessFlag());
        result.setMsg(ResultEnum.pub_success.getMsg());
        result.setData(object);
        return result;
    }
}
