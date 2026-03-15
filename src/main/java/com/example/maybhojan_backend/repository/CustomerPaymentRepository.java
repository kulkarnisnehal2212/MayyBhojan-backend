package com.example.maybhojan_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maybhojan_backend.model.CustomerPayment;

@Repository
public interface CustomerPaymentRepository extends JpaRepository<CustomerPayment,Long>{

    List<CustomerPayment> findByUserId(Long userId);


}