package com.jsan.jvaif.inf.constant;

/**
 * 返回结果描述
 * @author jcwang
 */
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
     * 其他通用异常
     */
    db_exception("101","数据库异常",false),

    /**
     * 公共
     */
    pub_fail("0", "提交失败!", false),
    pub_success("1", "提交成功!", true);
    
    private String code;
    private boolean successFlag;
    private String msg;

    public String getCode() {
        return code;
    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public String getMsg() {
        return msg;
    }

    ResultEnum(String code, String msg, boolean successFlag) {
        this.code = code;
        this.msg = msg;
        this.successFlag = successFlag;
    }
}