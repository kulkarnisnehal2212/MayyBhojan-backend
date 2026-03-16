package com.example.maybhojan_backend.controller;

import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    // CUSTOMER SIGNUP
    @PostMapping("/signup/customer")
    public User signupCustomer(@RequestBody User user) {

        user.setRole("CUSTOMER");
        user.setAccountStatus("ACTIVE");

        return userService.saveUser(user);
    }

    // HOMEMAKER SIGNUP (UPDATED)
    @PostMapping("/signup/homemaker")
    public User signupHomemaker(@RequestBody User user) {

        // This method automatically sets:
        // role = HOMEMAKER
        // account_status = PENDING
        // creates homemaker_profile
        return userService.registerHomemaker(user);
    }

    // DELIVERY SIGNUP
    @PostMapping("/signup/delivery")
    public User signupDelivery(@RequestBody User user) {

        user.setRole("DELIVERY");
        user.setAccountStatus("UNDER_REVIEW"); // wait for admin approval

        return userService.saveUser(user);
    }
    // LOGIN
    @PostMapping("/login")
    public User login(@RequestBody User request) {

        return userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );
    }
}