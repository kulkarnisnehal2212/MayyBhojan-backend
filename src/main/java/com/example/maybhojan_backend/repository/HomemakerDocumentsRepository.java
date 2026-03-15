package com.example.maybhojan_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.maybhojan_backend.model.HomemakerDocuments;
import com.example.maybhojan_backend.model.User;

import java.util.Optional;

public interface HomemakerDocumentsRepository extends JpaRepository<HomemakerDocuments, Long> {

    Optional<HomemakerDocuments> findByUser(User user);

}