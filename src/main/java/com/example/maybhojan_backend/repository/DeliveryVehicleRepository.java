package com.example.maybhojan_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maybhojan_backend.model.DeliveryVehicle;
import com.example.maybhojan_backend.model.User;

public interface DeliveryVehicleRepository
        extends JpaRepository<DeliveryVehicle, Long> {

    Optional<DeliveryVehicle> findByUser(User user);

}