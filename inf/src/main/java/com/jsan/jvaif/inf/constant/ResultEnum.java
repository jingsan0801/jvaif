package com.jsan.jvaif.inf.constant;

import lombok.Getter;

/**
 * 返回结果描述
 *
 * @author jcwang
 */
@Getter
public enum ResultEnum {

    /**
     * 成功
     */
    success_login("1000", "登录成功", true),

    exception_login("1001","登录失败",false),

    /**
     * 系统级异常 90xx
     */
    exception_common("9000", "系统异常,请联系我们", false),

    exception_param_null("9001", "参数对象为空", false),

    exception_db_sql_require("9002", "查询sql语句为空", false),

    exception_db("9003", "数据库异常", false),

    /**
     * 身份认证异常 91xx
     */
    exception_token_check_fail("9100", "身份验证失败", false),

    exception_user_exists("9101", "该用户已存在", false),

    exception_token_illegal("9104", "auth_token非法", false),

    exception_token_expired("9105", "auth_token过期", false),

    exception_token_required("9106", "auth_token不能为空", false),

    exception_token_decode_fail("9107", "解析token失败", false),

    /**
     * 用户异常 92xx
     */
    exception_user_notExists("9200", "该用户不存在", false),

    exception_user_invalid("9201", "该用户状态不正常", false),

    exception_user_login_fail("9203", "用户名或密码错误", false),

    /**
     * 授权异常 93xx
     */
    exception_auth_deny("9300", "没有授权", false),

    /**
     * 公共
     */
    pub_fail("0", "失败!", false),

    pub_success("1", "成功!", true);

    private String code;
    private boolean successFlag;
    private String msg;

    ResultEnum(String code, String msg, boolean successFlag) {
        this.code = code;
        this.msg = msg;
        this.successFlag = successFlag;
    }
}