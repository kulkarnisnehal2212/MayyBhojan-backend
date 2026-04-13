package com.example.maybhojan_backend.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.maybhojan_backend.model.*;
import com.example.maybhojan_backend.repository.*;

@Service
public class DeliveryPartnerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryIdentityRepository identityRepository;

    @Autowired
    private DeliveryDocumentsRepository documentsRepository;

    @Autowired
    private DeliveryVehicleRepository vehicleRepository;

    @Autowired
    private CloudinaryService cloudinaryService;


   
    @Autowired
    private DeliveryIdentityRepository deliveryIdentityRepository;

    public void saveIdentity(Long userId, LocalDate dob, String address){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update user role and status
        user.setRole("DELIVERY");
        user.setAccountStatus("UNDER_REVIEW");

        userRepository.save(user);

        DeliveryIdentity identity =
                deliveryIdentityRepository.findByUser(user)
                        .orElse(new DeliveryIdentity());

        identity.setUser(user);
        identity.setDob(dob);
        identity.setAddress(address);

        deliveryIdentityRepository.save(identity);
    }

    // UPLOAD DOCUMENTS
    public void uploadDocuments(Long userId,
                                MultipartFile idProof,
                                MultipartFile license){

        User user = userRepository.findById(userId).orElseThrow();

        String idUrl = cloudinaryService.uploadFile(idProof);
        String licenseUrl = cloudinaryService.uploadFile(license);

        DeliveryDocuments docs =
                documentsRepository.findByUser(user)
                .orElse(new DeliveryDocuments());

        docs.setUser(user);
        docs.setIdProofUrl(idUrl);
        docs.setLicenseUrl(licenseUrl);

        documentsRepository.save(docs);
    }


    // SAVE VEHICLE
    public void saveVehicle(Long userId,
                            String vehicleType,
                            String vehicleNumber){

        User user = userRepository.findById(userId).orElseThrow();

        DeliveryVehicle vehicle =
                vehicleRepository.findByUser(user)
                .orElse(new DeliveryVehicle());

        vehicle.setUser(user);
        vehicle.setVehicleType(vehicleType);
        vehicle.setVehicleNumber(vehicleNumber);

        vehicleRepository.save(vehicle);
    }

}