package com.jsan.jvaif.inf.util;

import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.vo.Result;

/**
 * @description: 返回Result的工具类
 * @author: jcwang
 * @create: 2019-08-01 21:29
 **/
public class ResultUtil {
    /**
     * @return 通用成功Result
     */
    public static Result success() {
        return new Result(ResultEnum.pub_success);
    }

    /**
     * @return 通用失败Result
     */
    public static Result fail() {
        return fail(ResultEnum.pub_fail);
    }

    /**
     * @param rsEnum 指定的resultEnum
     * @return 失败Result
     */
    public static Result fail(ResultEnum rsEnum) {
        return new Result(rsEnum);
    }

    /**
     * 指定内容的失败的Result
     *
     * @param code code
     * @param msg  msg
     * @param data data
     * @return 指定内容的失败的Result
     */
    public static Result fail(String code, String msg, String data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setSuccess(ResultEnum.pub_fail.isSuccessFlag());
        return result;

    }

    /**
     * 带数据的成功Result
     *
     * @param object 需要携带的数据
     * @return 带数据的成功Result
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.pub_success.getCode());
        result.setSuccess(ResultEnum.pub_success.isSuccessFlag());
        result.setMsg(ResultEnum.pub_success.getMsg());
        result.setData(object);
        return result;
    }
}
