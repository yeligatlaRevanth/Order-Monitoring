package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.OrderEvent;
import com.example.demo.kafka.OrderEventProducer;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderEventProducer = orderEventProducer;
    }

    @Transactional
    public Order placeOrder(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));

        Order order = new Order();
        order.setProduct(product);
        order.setOrderedBy(user);
        order.setName(product.getName());
        order.setPrice(product.getPrice());
        order.setStatus(OrderStatus.PLACED);

        Order savedOrder = orderRepository.save(order);

        orderEventProducer.publishOrderEvent(
                new OrderEvent(savedOrder.getId(), savedOrder.getStatus().name())
        );

        return savedOrder;
    }

    public List<Order> getMyOrders() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));

        return orderRepository.findByOrderedById(user.getId());
    }

    @Cacheable(value = "orders", key = "#id")
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !order.getOrderedBy().getUsername()
                .equals(authentication.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You cannot access this order");
        }

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}