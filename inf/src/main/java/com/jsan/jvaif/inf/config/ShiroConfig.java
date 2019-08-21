package com.jsan.jvaif.inf.config;

import com.jsan.jvaif.inf.domain.shiro.ApiRealm;
import com.jsan.jvaif.inf.filter.ApiTokenFilter;
import com.jsan.jvaif.inf.filter.ScyLogoutFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.jsan.jvaif.inf.constant.PublicConstant.*;

/**
 * @description: shiro配置类
 * @author: jcwang
 * @create: 2019-08-03 21:11
 **/
@Configuration
@PropertySource("classpath:config/pathConfig.properties")
public class ShiroConfig {
    @Value("#{'${static.urls}'.split(',')}")
    private List<String> staticUrls;

    /**
     * @return 身份认证Realm
     */
    @Bean
    public ApiRealm apiRealm() {
        return new ApiRealm();
    }

   /* *//**
     * cookie对象;
     * @return
     *//*
    private SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie(REMEMBER_ME_COOKIE_NAME);
        //cookie生效时间30天,单位秒;
        simpleCookie.setMaxAge(REMEMBER_ME_COOKIE_EXPIRE_DAY * 24 * 60 * 60);
        return simpleCookie;
    }

    *//**
     * cookie管理对象;记住我功能
     * @return
     *//*
    private CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // cookieRememberMeManager.setCipherKey用来设置加密的Key,参数类型byte[],字节数组长度要求16
        cookieRememberMeManager.setCipherKey(REMEMBER_ME_COOKIE_KEY.getBytes());
        return cookieRememberMeManager;
    }
*/

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(apiRealm());

        //关闭自带session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 使用remember me功能
        //securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }

    /**
     * 设置shiro的拦截器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");

        // 自定义filter
        Map<String, Filter> customFilter = new LinkedHashMap<>();
        // 可通过继承自带filter的方式实现自定义filter, put到customFilter中实现
        customFilter.put("api", new ApiTokenFilter());
        customFilter.put("logout",new ScyLogoutFilter());
        shiroFilterFactoryBean.setFilters(customFilter);

        // shiro自带filter
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 静态资源不过滤
        if(staticUrls != null) {
            for (String url : staticUrls) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }

        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/**", "api");

        // 这里的filter是从上向下依次执行, 一般将/** : anon放到最后

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.否则@RequiresRoles等注解无法生效
     * 使用代理方式;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
            new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 自动创建代理
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

}
