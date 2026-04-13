package com.example.maybhojan_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maybhojan_backend.model.DeliveryDocuments;
import com.example.maybhojan_backend.model.User;

public interface DeliveryDocumentsRepository
        extends JpaRepository<DeliveryDocuments, Long> {

    Optional<DeliveryDocuments> findByUser(User user);

}