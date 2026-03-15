package com.example.maybhojan_backend.controller;

import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ---------------------------
    // GET USER BY ID
    // ---------------------------
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ---------------------------
    // GET ALL USERS
    // ---------------------------
    @GetMapping
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

}