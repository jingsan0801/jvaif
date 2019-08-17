package com.jsan.jvaif.inf.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @description: 退出登录的filter
 * @author: jcwang
 * @create: 2019-08-17 17:26
 **/
public class ScyLogoutFilter extends LogoutFilter {
    /**
     * Acquires the currently executing {@link #getSubject(ServletRequest, ServletResponse) subject},
     * a potentially Subject or request-specific
     * {@link #getRedirectUrl(ServletRequest, ServletResponse, Subject) redirectUrl},
     * and redirects the end-user to that redirect url.
     *
     * @param request  the incoming ServletRequest
     * @param response the outgoing ServletResponse
     * @return {@code false} always as typically no further interaction should be done after user logout.
     * @throws Exception if there is any error.
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String url = "/logout_msg";
        subject.logout();
        issueRedirect(request, response, url);
        return false;
    }
}
