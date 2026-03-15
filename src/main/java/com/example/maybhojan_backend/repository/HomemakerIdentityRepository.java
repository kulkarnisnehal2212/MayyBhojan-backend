package com.example.maybhojan_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.maybhojan_backend.model.HomemakerIdentity;
import com.example.maybhojan_backend.model.User;

import java.util.Optional;

public interface HomemakerIdentityRepository extends JpaRepository<HomemakerIdentity, Long> {

    Optional<HomemakerIdentity> findByUser(User user);

}