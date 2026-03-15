package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maybhojan_backend.dto.CartItemDTO;
import com.example.maybhojan_backend.model.Cart;
import com.example.maybhojan_backend.repository.CartRepository;
import com.example.maybhojan_backend.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("http://localhost:5173")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(@RequestBody Cart cart){
        return cartService.addToCart(cart);
    }

    @GetMapping("/{userId}")
    public List<CartItemDTO> getCart(@PathVariable Long userId){
        return cartService.getCartItems(userId);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id){
        cartService.removeCart(id);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
}