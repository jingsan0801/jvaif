package com.jsan.jvaif.inf.constant;

import lombok.Getter;

/**
 * 返回结果描述
 * @author jcwang
 */
@Getter
public enum ResultEnum {

    /**
     * 参数异常
     */
    param_null("901","参数对象为空",false),

    /**
     * 必须的工作没做
     */
    require_sql("902","查询sql语句为空",false),

    /**
     * 身份认证
     */
    exception_authentication("903","身份验证失败",false),
    success_login("1","登录成功",true),

    exception_userName_exists("904","该用户已存在",false),

    /**
     * 其他通用异常
     */
    exception_db("101","数据库异常",false),


    /**
     * 公共
     */
    pub_fail("0", "提交失败!", false),
    pub_success("1", "提交成功!", true);

    
    private String code;
    private boolean successFlag;
    private String msg;


    ResultEnum(String code, String msg, boolean successFlag) {
        this.code = code;
        this.msg = msg;
        this.successFlag = successFlag;
    }
}