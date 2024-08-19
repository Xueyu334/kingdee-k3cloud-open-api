package com.rain.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * web服务器准备就绪时发布的事件。用于获取正在运行的服务器的本地端口
 *
 * @author xueyu
 */
@Slf4j
@Component
public class WebServerInitializedEventListener implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    public int getPort() {
        return this.serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
        log.info("WebServer port:{}", getPort());
    }
}
