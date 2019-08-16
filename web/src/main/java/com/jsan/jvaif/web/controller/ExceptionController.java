package com.jsan.jvaif.web.controller;

/**
 * @description: 处理exception的controller
 * @author: jcwang
 * @create: 2019-08-02 23:36
 **/

import com.jsan.jvaif.common.util.CommonUtil;
import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.exption.BusinessException;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    /**
     * 权限认证异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleUnauthorizedException(HttpServletRequest request, UnauthorizedException ex) {
        log.info(CommonUtil.getStackTraceInfoOfException(ex));
        return ResultUtil.fail(ResultEnum.exception_auth_deny, ex.getMessage());

    }

    /**
     * 身份认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
        log.info(CommonUtil.getStackTraceInfoOfException(ex));
        return ResultUtil.fail(ResultEnum.exception_token_check_fail, ex.getMessage());

    }

    /**
     * 系统自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleBusinessException(HttpServletRequest request, BusinessException ex) {
        log.info(CommonUtil.getStackTraceInfoOfException(ex));
        return ResultUtil.fail(ex.getResultEnum(), ex.getAdditionForJson());
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(MyBatisSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleDbException(HttpServletRequest request, MyBatisSystemException ex) {
        log.error(CommonUtil.getStackTraceInfoOfException(ex));
        return ResultUtil.fail(ResultEnum.exception_db, CommonUtil.getStackTraceInfoOfException(ex));
    }

    /**
     * 处理除了上面的其他异常
     *
     * @param request http请求
     * @param ex      异常
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result globalException(HttpServletRequest request, Throwable ex) {
        log.error(CommonUtil.getStackTraceInfoOfException(ex));
        return ResultUtil.fail(String.valueOf(this.getHttpStatus(request).value()), ex.getMessage(),
            CommonUtil.getStackTraceInfoOfException(ex));
    }

    /**
     * 获取http状态码
     *
     * @param request http请求
     * @return http状态码
     */
    private HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer httpStatus = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (httpStatus == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(httpStatus);

    }
}
