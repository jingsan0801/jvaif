package com.jsan.jvaif.inf.vo;

import com.jsan.jvaif.inf.constant.ResultEnum;
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
    private boolean success;
    private Object data;

    public Result() {

    }

    public Result(ResultEnum rs) {
        if(rs != null) {
            this.code = rs.getCode();
            this.msg = rs.getMsg();
            this.success = rs.isSuccessFlag();
        }
    }
}
