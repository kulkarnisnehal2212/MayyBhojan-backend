package com.example.maybhojan_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.maybhojan_backend.dto.CartItemDTO;
import com.example.maybhojan_backend.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    @Query("""
        SELECT new com.example.maybhojan_backend.dto.CartItemDTO(
            c.id,
            f.id,
            f.name,
            f.price,
            f.chef,
            c.qty
        )
        FROM Cart c
        JOIN Food f ON c.foodId = f.id
        WHERE c.userId = :userId
    """)
    List<CartItemDTO> getCartItems(Long userId);
}