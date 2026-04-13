package com.example.maybhojan_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maybhojan_backend.model.DeliveryIdentity;
import com.example.maybhojan_backend.model.User;

public interface DeliveryIdentityRepository
        extends JpaRepository<DeliveryIdentity, Long> {

    Optional<DeliveryIdentity> findByUser(User user);

}