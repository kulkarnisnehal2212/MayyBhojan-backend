package com.example.maybhojan_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.maybhojan_backend.dto.DeliveryIdentityRequest;
import com.example.maybhojan_backend.dto.VehicleRequest;
import com.example.maybhojan_backend.service.DeliveryPartnerService;

@RestController
@RequestMapping("/api/delivery-partner")
@CrossOrigin("http://localhost:5173")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;


    // STEP 1 : IDENTITY
    @PostMapping("/identity")
    public String saveIdentity(@RequestBody DeliveryIdentityRequest req){

        deliveryPartnerService.saveIdentity(
                req.getUserId(),
                req.getDob(),
                req.getAddress()
        );

        return "Identity saved successfully";
    }

    // STEP 2 : DOCUMENTS
    @PostMapping("/documents")
    public String uploadDocuments(
            @RequestParam("userId") Long userId,
            @RequestParam("idProof") MultipartFile idProof,
            @RequestParam("license") MultipartFile license) {

        deliveryPartnerService.uploadDocuments(userId, idProof, license);

        return "Documents uploaded successfully";
    }


    // STEP 3 : VEHICLE
    @PutMapping("/vehicle")
    public String saveVehicle(@RequestBody VehicleRequest req){

        deliveryPartnerService.saveVehicle(
                req.getUserId(),
                req.getVehicleType(),
                req.getVehicleNumber()
        );

        return "Vehicle saved successfully";
    }
}