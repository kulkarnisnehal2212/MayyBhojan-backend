package com.example.maybhojan_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maybhojan_backend.dto.AdminHomemakerDetailsDTO;
import com.example.maybhojan_backend.dto.DeliveryPartnerDetailsDTO;
import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.model.HomemakerIdentity;
import com.example.maybhojan_backend.model.HomemakerDocuments;
import com.example.maybhojan_backend.model.DeliveryDocuments;
import com.example.maybhojan_backend.model.DeliveryIdentity;
import com.example.maybhojan_backend.model.DeliveryVehicle;
import com.example.maybhojan_backend.model.HomemakerBankDetails;
import com.example.maybhojan_backend.repository.UserRepository;
import com.example.maybhojan_backend.repository.HomemakerIdentityRepository;
import com.example.maybhojan_backend.repository.HomemakerDocumentsRepository;
import com.example.maybhojan_backend.repository.DeliveryDocumentsRepository;
import com.example.maybhojan_backend.repository.DeliveryIdentityRepository;
import com.example.maybhojan_backend.repository.DeliveryVehicleRepository;
import com.example.maybhojan_backend.repository.HomemakerBankDetailsRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomemakerIdentityRepository identityRepository;

    @Autowired
    private HomemakerDocumentsRepository documentsRepository;

    @Autowired
    private HomemakerBankDetailsRepository bankRepository;
    @Autowired
    private DeliveryIdentityRepository deliveryIdentityRepository;

    @Autowired
    private DeliveryDocumentsRepository deliveryDocumentsRepository;

    @Autowired
    private DeliveryVehicleRepository deliveryVehicleRepository;

    public AdminHomemakerDetailsDTO getHomemakerDetails(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HomemakerIdentity identity = identityRepository.findByUser(user).orElse(null);
        HomemakerDocuments documents = documentsRepository.findByUser(user).orElse(null);
        HomemakerBankDetails bank = bankRepository.findByUser(user).orElse(null);

        AdminHomemakerDetailsDTO dto = new AdminHomemakerDetailsDTO();

        dto.setId(user.getId());
        dto.setName(identity != null && identity.getFullName() != null ? identity.getFullName() : user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(identity != null && identity.getPhone() != null ? identity.getPhone() : user.getPhone());

        // IDENTITY
        if(identity != null){

            dto.setAddress(identity.getAddress());

            if(identity.getDob() != null){
                dto.setDob(identity.getDob().toString());
            }

        }

        // BANK
        if(bank != null){

            dto.setAccountHolderName(bank.getAccountHolderName());
            dto.setAccountNumber(bank.getAccountNumber());
            dto.setIfscCode(bank.getIfscCode());

        }

        // DOCUMENTS
        if(documents != null){

            dto.setGovtIdUrl(documents.getGovtIdUrl());
            dto.setFssaiUrl(documents.getFssaiUrl());
            dto.setKitchenPhotoUrl(documents.getKitchenPhotoUrl());

        }

        return dto;
    }
    public DeliveryPartnerDetailsDTO getDeliveryPartnerDetails(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DeliveryIdentity identity =
                deliveryIdentityRepository.findByUser(user).orElse(null);

        DeliveryDocuments documents =
                deliveryDocumentsRepository.findByUser(user).orElse(null);

        DeliveryVehicle vehicle =
                deliveryVehicleRepository.findByUser(user).orElse(null);

        DeliveryPartnerDetailsDTO dto = new DeliveryPartnerDetailsDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());

        if(identity != null){

            dto.setAddress(identity.getAddress());

            if(identity.getDob() != null){
                dto.setDob(identity.getDob().toString());
            }

        }

        if(documents != null){

            dto.setIdProofUrl(documents.getIdProofUrl());
            dto.setLicenseUrl(documents.getLicenseUrl());

        }

        if(vehicle != null){

            dto.setVehicleType(vehicle.getVehicleType());
            dto.setVehicleNumber(vehicle.getVehicleNumber());

        }

        return dto;

    }
}