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
    @PutMapping("/accept/{orderId}")
    public Order acceptOrder(
            @PathVariable Long orderId,
            @RequestParam Long partnerId){

        return deliveryService.acceptOrder(orderId, partnerId);
    }

    // Active delivery orders
    @GetMapping("/active")
    public List<DeliveryOrderDTO> getActiveDeliveries(
            @RequestParam Long partnerId){

        return deliveryService.getActiveDeliveries(partnerId);
    }

    // Mark delivered
    @PutMapping("/deliver/{orderId}")
    public Order deliverOrder(@PathVariable Long orderId){
        return deliveryService.markDelivered(orderId);
    }

    // Wallet
    @GetMapping("/wallet")
    public List<DeliveryWalletDTO> getWallet(
            @RequestParam Long partnerId){

        return deliveryService.getWallet(partnerId);
    }

}