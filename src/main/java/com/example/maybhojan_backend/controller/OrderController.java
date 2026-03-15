package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.maybhojan_backend.dto.OrderDTO;
import com.example.maybhojan_backend.model.Order;
import com.example.maybhojan_backend.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // CUSTOMER → CREATE ORDER
    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }

    // ⭐ CUSTOMER → FETCH ORDER STATUS
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id){
        return orderService.getOrderById(id);
    }
 // ⭐ USER ORDERS
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId){
        return orderService.getOrdersByUser(userId);
    }


}