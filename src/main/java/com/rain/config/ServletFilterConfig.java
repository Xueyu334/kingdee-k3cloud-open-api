package com.rain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Servlet 配置
 *
 * @author xueyu
 */
@Slf4j
@Configuration
public class ServletFilterConfig {

    /**
     * 配置跨域过滤器 以支持在filter或者listener中进行数据处理
     *
     * @return {@linkplain   FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许cookies跨域
        corsConfiguration.setAllowCredentials(false);
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        corsConfiguration.setMaxAge(1800L);
        // 允许任何“源”(域名)使用
        corsConfiguration.addAllowedOrigin("*");
        // 允许任何请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法（get、post等）
        corsConfiguration.addAllowedMethod("*");
        // 处理所有请求的跨域配置
        source.registerCorsConfiguration("/**", corsConfiguration);
        // 注册自定义过滤器
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter(source));
        // 优先级最高
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

}
