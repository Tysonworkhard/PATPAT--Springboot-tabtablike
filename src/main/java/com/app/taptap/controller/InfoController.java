package com.app.taptap.controller;

import com.app.taptap.pojo.ConnectionInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    //处理前端info访问的问题
    @GetMapping("/websocket/info")
    public ConnectionInfo getWebSocketInfo() {
        ConnectionInfo info = new ConnectionInfo();
        info.setWebsocket("ws://10.8.0.1:9090/websocket");
        info.setOrigins(new String[]{"*"});
        return info;
    }
}
