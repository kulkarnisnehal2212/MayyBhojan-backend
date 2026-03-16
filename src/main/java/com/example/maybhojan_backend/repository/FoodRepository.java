
package com.example.maybhojan_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maybhojan_backend.model.Food;
import com.example.maybhojan_backend.model.User;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    // Get dishes by homemaker
    List<Food> findByHomemaker(User homemaker);

    // Get only available dishes for customers
    List<Food> findByAvailableTrue();

}

