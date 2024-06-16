package com.batalhanaval.config;

import com.batalhanaval.controller.GameController;
import com.batalhanaval.dtos.GameModel;
import com.batalhanaval.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.util.*;

public class SocketConnectionHandler extends TextWebSocketHandler {
    //https://medium.com/@parthiban.rajalingam/introduction-to-web-sockets-using-spring-boot-and-angular-b11e7363f051
    //https://www.geeksforgeeks.org/spring-boot-web-socket/
    public static Map<Long, WebSocketSession> webSocketSessions
            = new HashMap<>();

    private UserService userService;
    private GameController gameController;

    public SocketConnectionHandler(UserService userService, GameController gameController) {
        this.userService = userService;
        this.gameController = gameController;
    }

    @Override
    public void
    afterConnectionEstablished(WebSocketSession session)
            throws Exception
    {

        super.afterConnectionEstablished(session);
        // Logging the connection ID with Connected Message
        // Adding the session into the list
        webSocketSessions.put((Long) session.getAttributes().get("userId"), session);
    }

    @Override
    public void handleMessage(WebSocketSession session,
                              WebSocketMessage<?> message)
            throws Exception
    {

        //super.handleMessage(session, message);
        this.gameController.sendMessage(message);
    }
}
