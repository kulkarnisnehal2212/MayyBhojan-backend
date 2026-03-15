package com.example.maybhojan_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maybhojan_backend.dto.CartItemDTO;
import com.example.maybhojan_backend.model.Cart;
import com.example.maybhojan_backend.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(Cart cart){
        return cartRepository.save(cart);
    }

    public List<Cart> getCart(Long userId){
        return cartRepository.findByUserId(userId);
    }

    public void removeCart(Long id){
        cartRepository.deleteById(id);
    }

    @Transactional
    public void clearCart(Long userId){
        cartRepository.deleteByUserId(userId);
    }
    public List<CartItemDTO> getCartItems(Long userId){
        return cartRepository.getCartItems(userId);
    }
}