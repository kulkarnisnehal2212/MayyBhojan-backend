package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.maybhojan_backend.model.Address;
import com.example.maybhojan_backend.repository.AddressRepository;

@RestController
@RequestMapping("/api/address")
@CrossOrigin("http://localhost:5173")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping("/save")
    public Address saveAddress(@RequestBody Address address){
        return addressRepository.save(address);
    }

    // ⭐ NEW API
    @GetMapping("/user/{userId}")
    public List<Address> getUserAddresses(@PathVariable Long userId){
        return addressRepository.findByUserId(userId);
    }

}