package com.example.maybhojan_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maybhojan_backend.dto.AdminHomemakerDetailsDTO;
import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.model.HomemakerIdentity;
import com.example.maybhojan_backend.model.HomemakerDocuments;
import com.example.maybhojan_backend.model.HomemakerBankDetails;
import com.example.maybhojan_backend.repository.UserRepository;
import com.example.maybhojan_backend.repository.HomemakerIdentityRepository;
import com.example.maybhojan_backend.repository.HomemakerDocumentsRepository;
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

    public AdminHomemakerDetailsDTO getHomemakerDetails(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HomemakerIdentity identity = identityRepository.findByUser(user).orElse(null);
        HomemakerDocuments documents = documentsRepository.findByUser(user).orElse(null);
        HomemakerBankDetails bank = bankRepository.findByUser(user).orElse(null);

        AdminHomemakerDetailsDTO dto = new AdminHomemakerDetailsDTO();

        // USER
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());

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
}