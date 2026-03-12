package com.example.maybhojan_backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maybhojan_backend.model.Order;
import com.example.maybhojan_backend.repository.OrderItemRepository;
import com.example.maybhojan_backend.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order createOrder(Order order){
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("CONFIRMED");
        return orderRepository.save(order);
    }

}