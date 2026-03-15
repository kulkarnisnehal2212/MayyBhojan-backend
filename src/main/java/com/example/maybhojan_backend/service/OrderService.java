package com.example.maybhojan_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maybhojan_backend.dto.OrderDTO;
import com.example.maybhojan_backend.dto.OrderItemDTO;
import com.example.maybhojan_backend.model.Order;
import com.example.maybhojan_backend.model.OrderItem;
import com.example.maybhojan_backend.repository.OrderItemRepository;
import com.example.maybhojan_backend.repository.OrderRepository;
import com.example.maybhojan_backend.repository.DeliveryWalletRepository;
import com.example.maybhojan_backend.model.WalletTransaction;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private DeliveryWalletRepository walletRepository;
    // CREATE ORDER
    public Order createOrder(OrderDTO dto){

        Order order = new Order();

        order.setUserId(dto.getUserId());
        order.setAddressId(dto.getAddressId());
        order.setTotal(dto.getTotal());
        order.setPaymentMethod(dto.getPaymentMethod());

        order.setStatus("PLACED");

        order.setCreatedAt(LocalDateTime.now());

        // ZOMATO STYLE DELIVERY FEE
        double deliveryFee = calculateDeliveryFee(dto.getTotal());

        order.setDeliveryFee(deliveryFee);

        Order savedOrder = orderRepository.save(order);

        for(OrderItemDTO item : dto.getItems()){

            OrderItem oi = new OrderItem();

            oi.setOrderId(savedOrder.getId());
            oi.setFoodId(item.getFoodId());
            oi.setQty(item.getQty());
            oi.setPrice(item.getPrice());

            orderItemRepository.save(oi);

        }

        return savedOrder;
    }
    private double calculateDeliveryFee(double orderTotal){

        // Real app style logic

        if(orderTotal >= 500){
            return 0; // free delivery
        }

        if(orderTotal >= 300){
            return 20;
        }

        if(orderTotal >= 200){
            return 30;
        }

        return 40;
    }
    // DELIVERY DASHBOARD
    public List<Order> getAvailableOrders(){

        return orderRepository.findByStatus("PLACED");

    }

    // ACCEPT ORDER
    public Order acceptOrder(Long orderId){

        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus("IN_TRANSIT");
        order.setDeliveryPartnerId(1L);

        return orderRepository.save(order);

    }

    // MARK DELIVERED
    public Order markDelivered(Long orderId){

        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus("DELIVERED");

        orderRepository.save(order);

        WalletTransaction tx = new WalletTransaction();

        tx.setDeliveryPartnerId(order.getDeliveryPartnerId());
        tx.setOrderId(order.getId());
        tx.setAmount(order.getDeliveryFee());
        tx.setStatus("Completed");
        tx.setCreatedAt(LocalDateTime.now());

        walletRepository.save(tx);

        return order;
    }
    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow();
    }

    public List<Order> getOrdersByUser(Long userId){
        return orderRepository.findByUserId(userId);
    }
}