package com.example.maybhojan_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maybhojan_backend.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByStatus(String status);

	List<Order> findByUserId(Long userId);

    List<Order> findByDeliveryPartnerIdAndStatus(Long deliveryPartnerId, String status);

}
