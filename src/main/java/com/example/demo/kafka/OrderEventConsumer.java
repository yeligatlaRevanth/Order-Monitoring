
package com.example.demo.kafka;

import com.example.demo.dto.OrderEvent;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderWebSocketService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final OrderRepository orderRepository;
    private final OrderWebSocketService orderWebSocketService;

    public OrderEventConsumer(
            OrderRepository orderRepository,
            OrderWebSocketService orderWebSocketService) {
        this.orderRepository = orderRepository;
        this.orderWebSocketService = orderWebSocketService;
    }

    @KafkaListener(topics = "order-events", groupId = "order-monitoring-group")
    public void consume(OrderEvent event) throws InterruptedException {
        OrderStatus[] statuses = {
                OrderStatus.CONFIRMED,
                OrderStatus.PROCESSING,
                OrderStatus.SHIPPED,
                OrderStatus.DELIVERED
        };

        for (OrderStatus status : statuses) {

            Order order = orderRepository.findById(event.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            if (order.getStatus() == OrderStatus.CANCELLED) {
                return;
            }

            order.setStatus(status);
            Order updatedOrder = orderRepository.save(order);

            orderWebSocketService.sendOrderUpdate(
                    new OrderEvent(
                            updatedOrder.getId(),
                            updatedOrder.getStatus().name()
                    )
            );

            orderRepository.flush();

            evictOrderCache(updatedOrder.getId());
        }
    }

    @CacheEvict(value = "orders", key = "#orderId")
    public void evictOrderCache(Long orderId) {
    }
}
