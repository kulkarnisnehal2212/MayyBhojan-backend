package com.example.maybhojan_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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


    // ------------------------------
    // Homemaker Registration
    // ------------------------------

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

    public HomemakerProfile getHomemakerProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HomemakerProfile profile = homemakerProfileRepository.findByUser(user).orElseGet(() -> {
            HomemakerProfile p = new HomemakerProfile();
            p.setUser(user);
            p.setOnboardingStep(1);
            p.setIdentityVerified(false);
            p.setDocumentsUploaded(false);
            p.setAuditCompleted(false);
            p.setBankAdded(false);
            p.setIsAvailable(false);
            return homemakerProfileRepository.save(p);
        });

        // Auto-detect completed steps from actual data tables
        boolean identityDone = identityRepository.findByUser(user).isPresent();
        boolean docsDone = documentsRepository.findByUser(user).isPresent();
        boolean bankDone = bankRepository.findByUser(user).isPresent();
        boolean auditDone = user.getAccountStatus().equals("UNDER_REVIEW")
                         || user.getAccountStatus().equals("ACTIVE");

        boolean changed = false;
        if (identityDone != profile.getIdentityVerified()) { profile.setIdentityVerified(identityDone); changed = true; }
        if (docsDone != profile.getDocumentsUploaded())    { profile.setDocumentsUploaded(docsDone);    changed = true; }
        if (bankDone != profile.getBankAdded())            { profile.setBankAdded(bankDone);            changed = true; }
        if (auditDone != profile.getAuditCompleted())      { profile.setAuditCompleted(auditDone);      changed = true; }
        if (changed) homemakerProfileRepository.save(profile);

        return profile;

    }

    // ------------------------------
    // Login
    // ------------------------------

    // --------------------------------------------------
    // LOGIN
    // --------------------------------------------------

    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        String status = user.getAccountStatus();

        if (status.equals("REJECTED")) {
            throw new RuntimeException("Your application was rejected");
        }

        return user;
    }


    // ------------------------------
    // Identity Step
    // ------------------------------


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

    // ------------------------------
    // Upload Documents (Cloudinary)
    // ------------------------------


    // --------------------------------------------------
    // STEP 2 - DOCUMENT UPLOAD
    // --------------------------------------------------
    public String uploadDocuments(Long userId,
            MultipartFile govtId,
            MultipartFile fssai,
            MultipartFile kitchenPhoto) {

User user = userRepository.findById(userId)
.orElseThrow(() -> new RuntimeException("User not found"));

// CHECK if documents already exist
Optional<HomemakerDocuments> existingDocs =
documentsRepository.findByUser(user);

if (existingDocs.isPresent()) {
return "Documents already submitted";
}

// Upload to Cloudinary
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

return "Documents uploaded successfully";
}

    // ------------------------------
    // Bank Details Step
    // ------------------------------

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
    // ------------------------------
    // Submit Application to Admin
    // ------------------------------


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

        profile.setAuditCompleted(true);
        homemakerProfileRepository.save(profile);
    }
 // --------------------------------------------------
 // ADMIN APPROVE HOMEMAKER
 // --------------------------------------------------

 public void approveHomemaker(Long userId) {

     User user = userRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("User not found"));

     user.setAccountStatus("APPROVED");

     userRepository.save(user);

 }


 // --------------------------------------------------
 // ADMIN REJECT HOMEMAKER
 // --------------------------------------------------

 public void rejectHomemaker(Long userId) {

     User user = userRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("User not found"));

     user.setAccountStatus("REJECTED");

     userRepository.save(user);

 }
}