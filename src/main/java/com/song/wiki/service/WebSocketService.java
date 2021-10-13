package com.song.wiki.service;

import com.song.wiki.domain.Doc;
import com.song.wiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//单独写一个类让异步化生效
@Service
public class WebSocketService {

    @Resource
    private WebSocketServer webSocketServer;

    //加上Async注解后，spring会为这个类生成一个代理类
    //在同一个类里调用此方法，Async注解不起作用
    @Async
    public void sendInfo(String message, String logId){
        MDC.put("LOG_ID", logId);
        webSocketServer.sendInfo(message);
    }
}
