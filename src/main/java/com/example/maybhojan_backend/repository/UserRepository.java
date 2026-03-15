package com.example.maybhojan_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.maybhojan_backend.model.User;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // -------------------------
    // Find user by email (login)
    // -------------------------
    Optional<User> findByEmail(String email);

    // -------------------------
    // Find users by role
    // Example: get all homemakers
    // -------------------------
    List<User> findByRole(String role);

    // -------------------------
    // Find users by role and account status
    // Example: admin sees pending homemakers
    // -------------------------
    List<User> findByRoleAndAccountStatus(String role, String accountStatus);

}