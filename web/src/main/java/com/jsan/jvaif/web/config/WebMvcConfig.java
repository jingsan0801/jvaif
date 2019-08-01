package com.jsan.jvaif.web.config;

import com.jsan.jvaif.web.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 配置web资源映射
 * @author: jcwang
 * @create: 2019-07-30 16:03
 **/
@Configuration
@PropertySource("classpath:sys.config.properties")
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将resources下的目录做映射
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/error")
            .excludePathPatterns("/scy/**");
    }
}
