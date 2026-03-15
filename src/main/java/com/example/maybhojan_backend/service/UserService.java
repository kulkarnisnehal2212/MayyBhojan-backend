package com.example.maybhojan_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.model.HomemakerProfile;
import com.example.maybhojan_backend.model.HomemakerIdentity;
import com.example.maybhojan_backend.model.HomemakerDocuments;
import com.example.maybhojan_backend.model.HomemakerBankDetails;

import com.example.maybhojan_backend.dto.IdentityRequest;

import com.example.maybhojan_backend.repository.UserRepository;
import com.example.maybhojan_backend.repository.HomemakerProfileRepository;
import com.example.maybhojan_backend.repository.HomemakerIdentityRepository;
import com.example.maybhojan_backend.repository.HomemakerDocumentsRepository;
import com.example.maybhojan_backend.repository.HomemakerBankDetailsRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomemakerProfileRepository homemakerProfileRepository;

    @Autowired
    private HomemakerIdentityRepository identityRepository;

    @Autowired
    private HomemakerDocumentsRepository documentsRepository;

    @Autowired
    private HomemakerBankDetailsRepository bankRepository;

    @Autowired
    private CloudinaryService cloudinaryService;


    // --------------------------------------------------
    // GENERIC USER SAVE (CUSTOMER / DELIVERY / ADMIN)
    // --------------------------------------------------
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    // --------------------------------------------------
    // HOMEMAKER REGISTRATION (SPECIAL FLOW)
    // --------------------------------------------------
    public User registerHomemaker(User user) {

        user.setRole("HOMEMAKER");
        user.setAccountStatus("PENDING");

        User savedUser = userRepository.save(user);

        HomemakerProfile profile = new HomemakerProfile();
        profile.setUser(savedUser);
        profile.setOnboardingStep(1);
        profile.setIdentityVerified(false);
        profile.setDocumentsUploaded(false);
        profile.setAuditCompleted(false);
        profile.setBankAdded(false);
        profile.setIsAvailable(false);

        homemakerProfileRepository.save(profile);

        return savedUser;
    }


    // --------------------------------------------------
    // LOGIN
    // --------------------------------------------------
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.getAccountStatus().equals("ACTIVE")) {
            throw new RuntimeException("Your account is waiting for admin approval");
        }

        return user;
    }


    // --------------------------------------------------
    // STEP 1 - IDENTITY
    // --------------------------------------------------
    public void saveIdentity(IdentityRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        HomemakerIdentity identity = new HomemakerIdentity();

        identity.setUser(user);
        identity.setFullName(request.getFullName());
        identity.setPhone(request.getPhone());
        identity.setDob(request.getDob());
        identity.setAddress(request.getAddress());

        identityRepository.save(identity);

        HomemakerProfile profile = homemakerProfileRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setIdentityVerified(true);
        profile.setOnboardingStep(2);

        homemakerProfileRepository.save(profile);
    }


    // --------------------------------------------------
    // STEP 2 - DOCUMENT UPLOAD
    // --------------------------------------------------
    public void uploadDocuments(Long userId,
                                MultipartFile govtId,
                                MultipartFile fssai,
                                MultipartFile kitchenPhoto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String govtIdUrl = cloudinaryService.uploadFile(govtId);
        String fssaiUrl = cloudinaryService.uploadFile(fssai);
        String kitchenPhotoUrl = cloudinaryService.uploadFile(kitchenPhoto);

        HomemakerDocuments docs = new HomemakerDocuments();

        docs.setUser(user);
        docs.setGovtIdUrl(govtIdUrl);
        docs.setFssaiUrl(fssaiUrl);
        docs.setKitchenPhotoUrl(kitchenPhotoUrl);

        documentsRepository.save(docs);

        HomemakerProfile profile = homemakerProfileRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setDocumentsUploaded(true);
        profile.setOnboardingStep(3);

        homemakerProfileRepository.save(profile);
    }


    // --------------------------------------------------
    // STEP 3 - BANK DETAILS
    // --------------------------------------------------
    public void saveBankDetails(Long userId,
                                String accountHolderName,
                                String accountNumber,
                                String ifscCode) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HomemakerBankDetails bank = new HomemakerBankDetails();

        bank.setUser(user);
        bank.setAccountHolderName(accountHolderName);
        bank.setAccountNumber(accountNumber);
        bank.setIfscCode(ifscCode);

        bankRepository.save(bank);

        HomemakerProfile profile = homemakerProfileRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setBankAdded(true);
        profile.setOnboardingStep(4);

        homemakerProfileRepository.save(profile);
    }


    // --------------------------------------------------
    // FINAL SUBMISSION TO ADMIN
    // --------------------------------------------------
    public void submitForReview(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HomemakerProfile profile = homemakerProfileRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (!profile.getIdentityVerified()
                || !profile.getDocumentsUploaded()
                || !profile.getBankAdded()) {

            throw new RuntimeException("Please complete all steps before submitting");
        }

        user.setAccountStatus("UNDER_REVIEW");

        userRepository.save(user);
    }
}