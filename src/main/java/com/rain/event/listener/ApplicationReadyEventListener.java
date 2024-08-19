package com.rain.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

/**
 * listen For Spring Application Ready Events
 *
 * @author xueyu
 */
@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Duration timeTaken = event.getTimeTaken();
        Duration abs = timeTaken.abs();
        long millis = abs.toMillis();
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        log.info("程序:[{}]启动成功==========>耗时:<{}>(ms)", applicationContext.getApplicationName(), millis);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        log.info("- Local: http://localhost:{}{}", port, contextPath);
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            log.info("- Network: http://{}:{}{}", ip, port, contextPath);
        } catch (UnknownHostException e) {
            log.error("get local host fail:", e);
        }
    }
}
