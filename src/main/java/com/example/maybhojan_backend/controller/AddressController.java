package com.example.maybhojan_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maybhojan_backend.model.Address;
import com.example.maybhojan_backend.repository.AddressRepository;

@RestController
@RequestMapping("/api/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping("/save")
    public Address saveAddress(@RequestBody Address address){
        return addressRepository.save(address);
    }

}
