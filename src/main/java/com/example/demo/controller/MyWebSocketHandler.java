package com.example.demo.controller;

import com.example.demo.elevator.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private Building building;

    // 存储所有连接的会话
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("客户端连接: " + session.getId());
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("收到消息: " + payload);

        building.receiveRequest(payload);
    }

    /**
     * 主动向前端发送消息的方法
     */
    public static void sendMessageToClient(WebSocketSession session, String message) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播给所有连接的客户端
     */
    public static void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            sendMessageToClient(session, message);
        }
    }
}