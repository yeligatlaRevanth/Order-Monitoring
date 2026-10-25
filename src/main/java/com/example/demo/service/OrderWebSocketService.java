package com.example.demo.service;

import com.example.demo.dto.OrderEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public OrderWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOrderUpdate(OrderEvent event) {
        messagingTemplate.convertAndSend("/topic/orders", event);
    }
}