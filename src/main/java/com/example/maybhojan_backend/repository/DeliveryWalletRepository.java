package com.example.maybhojan_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maybhojan_backend.model.WalletTransaction;

public interface DeliveryWalletRepository 
       extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByDeliveryPartnerId(Long deliveryPartnerId);

}