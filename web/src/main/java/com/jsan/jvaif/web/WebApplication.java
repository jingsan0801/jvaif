package com.jsan.jvaif.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * web入口
 *
 * @author jcwang
 */
@SpringBootApplication
@ComponentScan({"com.jsan.jvaif"})
@MapperScan({"com.jsan.jvaif.inf.mapper"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
