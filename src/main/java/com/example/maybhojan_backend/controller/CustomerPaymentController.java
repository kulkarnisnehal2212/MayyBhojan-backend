package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maybhojan_backend.model.CustomerPayment;
import com.example.maybhojan_backend.repository.CustomerPaymentRepository;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("http://localhost:5173")
public class CustomerPaymentController {

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;

    @GetMapping("/{userId}")
    public List<CustomerPayment> getPayments(@PathVariable Long userId){
        return customerPaymentRepository.findByUserId(userId);
    }

    @PostMapping
    public CustomerPayment addPayment(@RequestBody CustomerPayment customerPayment){
        return customerPaymentRepository.save(customerPayment);
    }

}