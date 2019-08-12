package com.jsan.jvaif.web.config;

import com.jsan.jvaif.web.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @description: 配置web资源映射
 * @author: jcwang
 * @create: 2019-07-30 16:03
 **/
@Configuration
@PropertySource("classpath:config/pathConfig.properties")
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("#{'${static.urls}'.split(',')}")
    private List<String> staticUrls;

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        // 这里要提前注入拦截器,否则会导致拦截器内的service注入失败
        return new LoginInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将resources下的目录做映射
        // swagger
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration =
            registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns(staticUrls);
    }

}
