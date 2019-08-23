package com.jsan.jvaif.inf.config;

import com.jsan.jvaif.inf.domain.shiro.ApiRealm;
import com.jsan.jvaif.inf.domain.shiro.SysRealm;
import com.jsan.jvaif.inf.filter.ApiTokenFilter;
import com.jsan.jvaif.inf.filter.ScyLogoutFilter;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
     * 自定义realm管理器
     * 多个realm存在时使用
     *
     * @return
     */
    @Bean
    public CustomerModularRealmAuthenticator customerModularRealmAuthenticator() {
        CustomerModularRealmAuthenticator customerModularRealmAuthenticator = new CustomerModularRealmAuthenticator();
        // 配置策略
        // 只要有一个Realm验证成功即可，和FirstSuccessfulStrategy不同，返回所有Realm身份验证成功的认证信息.
        customerModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return customerModularRealmAuthenticator;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义认证器
        securityManager.setAuthenticator(customerModularRealmAuthenticator());
        List<Realm> realmList = new ArrayList<>();
        realmList.add(apiRealm());
        realmList.add(sysRealm());
        securityManager.setRealms(realmList);

        //关闭自带session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * @return api身份认证Realm
     */
    @Bean
    public ApiRealm apiRealm() {
        return new ApiRealm();
    }

    /**
     * @return api身份认证Realm
     */
    @Bean
    public SysRealm sysRealm() {
        return new SysRealm();
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
        customFilter.put("logout", new ScyLogoutFilter());
        shiroFilterFactoryBean.setFilters(customFilter);

        // shiro自带filter
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 静态资源不过滤
        if (staticUrls != null) {
            for (String url : staticUrls) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }

        filterChainDefinitionMap.put("/logout", "logout");
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
