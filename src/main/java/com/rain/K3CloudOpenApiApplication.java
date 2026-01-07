package com.rain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScans(value = {@ComponentScan(basePackages = {"com.rain", "com.kingdee"})})
public class K3CloudOpenApiApplication {


    public static void main(String[] args) {
        log.info("start application===>");
        SpringApplication.run(K3CloudOpenApiApplication.class, args);
    }

}
