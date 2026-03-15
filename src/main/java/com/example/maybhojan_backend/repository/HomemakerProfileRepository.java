package com.example.maybhojan_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.maybhojan_backend.model.HomemakerProfile;
import com.example.maybhojan_backend.model.User;

import java.util.Optional;

public interface HomemakerProfileRepository extends JpaRepository<HomemakerProfile, Long> {

    Optional<HomemakerProfile> findByUser(User user);
    
    

}



