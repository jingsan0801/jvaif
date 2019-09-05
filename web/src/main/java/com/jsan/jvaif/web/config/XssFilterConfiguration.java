package com.jsan.jvaif.web.config;

import com.jsan.jvaif.web.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * xss过滤拦截器
 * @author jcwang
 */
//@Configuration
public class XssFilterConfiguration {
    /**
     * xss过滤拦截器
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean initXssFilterBean = new FilterRegistrationBean();
        initXssFilterBean.setFilter(new XssFilter());
        initXssFilterBean.setOrder(1);
        initXssFilterBean.setEnabled(true);
        initXssFilterBean.addUrlPatterns("/*");
        initXssFilterBean.setDispatcherTypes(DispatcherType.REQUEST);
        return initXssFilterBean;
    }
}