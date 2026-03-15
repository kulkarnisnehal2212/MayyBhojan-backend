package com.example.maybhojan_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maybhojan_backend.dto.DeliveryOrderDTO;
import com.example.maybhojan_backend.dto.DeliveryWalletDTO;
import com.example.maybhojan_backend.model.Address;
import com.example.maybhojan_backend.model.Delivery;
import com.example.maybhojan_backend.model.Order;
import com.example.maybhojan_backend.repository.AddressRepository;
import com.example.maybhojan_backend.repository.DeliveryRepository;
import com.example.maybhojan_backend.repository.OrderRepository;

@Service
public class DeliveryService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private AddressRepository addressRepository;


    // ACTIVE DELIVERIES
    public List<DeliveryOrderDTO> getActiveDeliveries(Long partnerId){

        List<Delivery> deliveries =
                deliveryRepository.findByDeliveryPartnerId(partnerId);

        return deliveries.stream().map(delivery -> {

            Order order =
                    orderRepository.findById(delivery.getOrderId())
                    .orElse(null);

            if(order == null) return null;

            Address address =
                    addressRepository.findById(order.getAddressId())
                    .orElse(null);

            DeliveryOrderDTO dto = new DeliveryOrderDTO();

            dto.setId(order.getId());
            dto.setStatus(order.getStatus());
            dto.setTotal(order.getTotal());
            dto.setDeliveryFee(order.getDeliveryFee());

            if(address != null){
                dto.setHouse(address.getHouse());
                dto.setArea(address.getArea());
                dto.setLandmark(address.getLandmark());
                dto.setPincode(address.getPincode());
            }

            return dto;

        }).toList();

    }


    // ACCEPT ORDER
    public Order acceptOrder(Long orderId, Long partnerId){

        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus("IN_TRANSIT");
        order.setDeliveryPartnerId(partnerId);

        orderRepository.save(order);

        Delivery delivery = new Delivery();

        delivery.setOrderId(orderId);
        delivery.setDeliveryPartnerId(partnerId);
        delivery.setStatus("IN_TRANSIT");

        deliveryRepository.save(delivery);

        return order;

    }


    // MARK DELIVERED
    public Order markDelivered(Long orderId){

        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus("DELIVERED");

        orderRepository.save(order);

        Delivery delivery =
                deliveryRepository.findByOrderId(orderId);

        delivery.setStatus("DELIVERED");

        deliveryRepository.save(delivery);

        return order;

    }


    // AVAILABLE ORDERS
    public List<DeliveryOrderDTO> getAvailableOrders(){

        List<Order> orders =
                orderRepository.findByStatus("PLACED");

        return orders.stream().map(order -> {

            Address address =
                    addressRepository.findById(order.getAddressId())
                    .orElse(null);

            DeliveryOrderDTO dto = new DeliveryOrderDTO();

            dto.setId(order.getId());
            dto.setStatus(order.getStatus());
            dto.setTotal(order.getTotal());
            dto.setDeliveryFee(order.getDeliveryFee());

            if(address != null){
                dto.setHouse(address.getHouse());
                dto.setArea(address.getArea());
                dto.setLandmark(address.getLandmark());
                dto.setPincode(address.getPincode());
            }

            return dto;

        }).toList();

    }


    // WALLET
    public List<DeliveryWalletDTO> getWallet(Long partnerId){

        List<Order> orders =
                orderRepository.findByDeliveryPartnerIdAndStatus(
                        partnerId,
                        "DELIVERED"
                );

        return orders.stream().map(order -> {

            DeliveryWalletDTO dto = new DeliveryWalletDTO();

            dto.setId(order.getId());
            dto.setOrderId(order.getId());
            dto.setAmount(order.getDeliveryFee());
            dto.setCreatedAt(order.getCreatedAt());
            dto.setStatus("Completed");

            return dto;

        }).toList();

    }

}