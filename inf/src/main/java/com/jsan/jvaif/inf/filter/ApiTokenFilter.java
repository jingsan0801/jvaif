package com.jsan.jvaif.inf.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.jsan.jvaif.common.util.CommonUtil;
import com.jsan.jvaif.inf.constant.PublicConstant;
import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.domain.ApiToken;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.sys.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 通过jwt方式实现的token验证filter, 在interceptor之前触发
 * <p>
 * 没有带token的情况下,ApiTokenFilter会验证通过. 由后面的interceptor(LoginInterceptor)处理
 * <p>
 * 这里发生的异常,ExceptionHandler是捕获不到的, 所以身份验证不通过时
 * 通过response401() 跳转到controller, 由exceptionController统一接管
 * @author: jcwang
 * @create: 2019-08-12 10:40
 **/
public class ApiTokenFilter extends BasicHttpAuthenticationFilter {
    /**
     * 检测Header里Authorization字段
     * 是否登录尝试
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest)request;
        String authToken = req.getHeader(PublicConstant.REQUEST_AUTH_HEADER);
        return !StringUtils.isEmpty(authToken);
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String authToken = ((HttpServletRequest)request).getHeader(PublicConstant.REQUEST_AUTH_HEADER);
        ApiToken apiToken = new ApiToken(authToken);
        // 给shiro的realm进行login
        getSubject(request, response).login(apiToken);
        return true;
    }

    /**
     * 是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue 对应在ShiroConfig中配置的filter map
     * @return true: 则onAccessDenied方法不会继续执行; false: 调用onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean rs = true;
        ResultEnum resultEnum;
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                rs = false;
                if ((e instanceof SignatureVerificationException) || (e instanceof AuthenticationException)) {
                    response401(request, response, ResultUtil.fail(ResultEnum.exception_token_illegal, e.getMessage()));
                } else if (e instanceof TokenExpiredException) {
                    response401(request, response, ResultUtil.fail(ResultEnum.exception_token_expired, e.getMessage()));
                } else {
                    response401(request, response,
                        ResultUtil.fail(ResultEnum.exception_common, CommonUtil.getStackTraceInfoOfException(e)));
                }
            }
        }
        // 没有带token的情况下,也返回true. 由后面的interceptor(LoginInterceptor)处理
        return rs;
    }

    /**
     * 表示访问拒绝时是否自己处理，如果返回 true 表示自己不处理且继续拦截器链执行，返回 false 表示自己已经处理了（比如重定向到另一个页面）。
     *
     * @param request  incoming ServletRequest
     * @param response outgoing ServletResponse
     * @return true if the request should be processed; false if the request should not continue to be processed
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 401非法请求
     *
     * @param req
     * @param resp
     */
    private void response401(ServletRequest req, ServletResponse resp, Result rs) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)req;
        HttpServletResponse httpServletResponse = (HttpServletResponse)resp;
        try {
            // 这个类里发生的异常,ExceptionHandler是捕获不到的, 所以身份验证不通过时
            // 通过response401() 跳转到controller, 由exceptionController统一接管
            httpServletRequest.setAttribute("rs", rs);
            req.getRequestDispatcher("/login_msg").forward(httpServletRequest, httpServletResponse);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
