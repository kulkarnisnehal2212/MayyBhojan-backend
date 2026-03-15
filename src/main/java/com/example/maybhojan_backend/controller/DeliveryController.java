package com.example.maybhojan_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.maybhojan_backend.dto.DeliveryOrderDTO;
import com.example.maybhojan_backend.dto.DeliveryWalletDTO;
import com.example.maybhojan_backend.model.Order;
import com.example.maybhojan_backend.service.DeliveryService;

@RestController
@RequestMapping("/api/delivery")
@CrossOrigin("http://localhost:5173")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // Available orders
    @GetMapping("/orders")
    public List<DeliveryOrderDTO> getOrders(){
        return deliveryService.getAvailableOrders();
    }

    // Accept order
    @PutMapping("/accept/{id}")
    public Order acceptOrder(@PathVariable Long id){
        return deliveryService.acceptOrder(id);
    }

    // Active delivery orders
    @GetMapping("/active")
    public List<DeliveryOrderDTO> getActiveDeliveries(){
        return deliveryService.getActiveDeliveries();
    }

    // Mark delivered
    @PutMapping("/deliver/{id}")
    public Order deliverOrder(@PathVariable Long id){
        return deliveryService.markDelivered(id);
    }
    @GetMapping("/wallet/{partnerId}")
    public List<DeliveryWalletDTO> getWallet(@PathVariable Long partnerId){
        return deliveryService.getWallet(partnerId);
    }
}