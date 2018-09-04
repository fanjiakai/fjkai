package com.ddian.youfan.admin;

import com.ddian.youfan.admin.utils.MD5Utils;
import com.ddian.youfan.common.filter.ContextFilter;
import com.ddian.youfan.common.intercepter.PaginationInterceptor;
import com.ddian.youfan.common.persistence.dialect.db.PostgreSQLDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println(MD5Utils.encrypt("admin", "gykj_123"));
       /* UserToken userToken = new UserToken("admin", "1", "超级管理员");
        try {
            String token = JwtUtils.generateToken(userToken, 300 * 60 * 1000);
            System.out.println(token);
        } catch (Exception e) {

        }*/
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new ContextFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }


    // 开启跨数据库分页组件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor(new MySQLDialect());

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor(new PostgreSQLDialect());
        return paginationInterceptor;
    }
}
