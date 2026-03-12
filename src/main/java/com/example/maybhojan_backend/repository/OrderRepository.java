package com.example.maybhojan_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maybhojan_backend.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
