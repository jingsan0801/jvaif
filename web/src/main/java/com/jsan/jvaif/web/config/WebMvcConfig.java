package com.jsan.jvaif.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 配置web资源映射
 * @author: jcwang
 * @create: 2019-07-30 16:03
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将resources下的目录做映射
        registry.addResourceHandler("/demo/**").addResourceLocations("classpath:/demo/");
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
    }
}
