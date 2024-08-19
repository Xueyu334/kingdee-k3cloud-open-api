package com.rain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@Slf4j
@SpringBootApplication
@ComponentScans(value = {@ComponentScan(basePackages = {"com.rain", "com.kingdee"})})
public class K3CloudOpenApiApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        log.info("start application===>");
        SpringApplication.run(K3CloudOpenApiApplication.class, args);
    }

    /**
     * 配置应用程序以便在传统的 Servlet 容器中运行。
     * <p>
     * 这个方法重写了 {@link SpringBootServletInitializer#configure} 方法，
     * 用于指定 Spring Boot 应用的主类。当应用程序以 WAR 文件的形式部署到
     * Servlet 容器（如 Tomcat 或 Jetty）中时，此方法确保应用程序正确初始化和配置。
     * </p>
     *
     * @param builder 应用程序上下文构建器
     * @return 配置了应用程序主类的应用程序构建器
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(K3CloudOpenApiApplication.class);
    }

}
