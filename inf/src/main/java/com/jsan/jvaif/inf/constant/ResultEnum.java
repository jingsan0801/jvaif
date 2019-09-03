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
     * code统一为1, 这样前台判断为1的都是成功, 更好判断是否成功
     */
    success_login("1", "登录成功", true),

    success_logout("1", "退出成功", true),

    success_image_code_check("1", "图形验证码校验通过", true),

    /**
     * 系统级异常 90xx
     */
    exception_common("9000", "系统异常,请联系我们", false),

    exception_param_required("9001", "参数校验异常", false),

    exception_db_sql_required("9002", "查询sql语句为空", false),

    exception_db("9003", "数据库异常", false),

    /**
     * 身份认证异常 91xx
     */

    exception_login("9100", "登录失败", false),

    exception_token_check_fail("9101", "用户名或密码错误", false),

    exception_user_exists("9102", "该用户已存在", false),

    exception_token_illegal("9103", "auth_token非法", false),

    exception_token_expired("9104", "auth_token过期", false),

    exception_token_required("9105", "auth_token不能为空", false),

    exception_token_decode_fail("9106", "解析token失败", false),

    exception_image_code_generator("9107", "图形验证码生成异常", false),

    exception_image_code_check("9108", "图形验证码校验不通过", false),

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

    exception_res_getMenu("9301","获取系统菜单失败",false),

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