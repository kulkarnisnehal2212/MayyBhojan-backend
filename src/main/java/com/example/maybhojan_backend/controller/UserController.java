package com.example.maybhojan_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.maybhojan_backend.model.HomemakerProfile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.service.UserService;
import com.example.maybhojan_backend.dto.IdentityRequest;
import com.example.maybhojan_backend.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // ------------------------------
    // REGISTER HOMEMAKER
    // ------------------------------
    @PostMapping("/homemaker/register")
    public User registerHomemaker(@RequestBody User user) {
        return userService.registerHomemaker(user);
    }

    // ------------------------------
    // LOGIN
    // ------------------------------
    @PostMapping("/users/login")
    public User login(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

    // ------------------------------
    // GET USER BY ID
    // ------------------------------
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ------------------------------
    // GET ALL USERS
    // ------------------------------
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ------------------------------
    // STEP 1 - IDENTITY
    // ------------------------------
    @PostMapping("/homemaker/identity")
    public String saveIdentity(@RequestBody IdentityRequest request) {

        userService.saveIdentity(request);

        return "Identity saved successfully";
    }

    // ------------------------------
    // STEP 2 - DOCUMENT UPLOAD
    // ------------------------------
    @PostMapping(
    	    value = "/homemaker/documents",
    	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    	)
    	public String uploadDocuments(
    	        @RequestParam Long userId,
    	        @RequestParam MultipartFile govtId,
    	        @RequestParam MultipartFile fssai,
    	        @RequestParam MultipartFile kitchenPhoto) {

    	    return userService.uploadDocuments(userId, govtId, fssai, kitchenPhoto);
    	}
    // ------------------------------
    // STEP 3 - BANK DETAILS
    // ------------------------------
    @PostMapping("/homemaker/bank")
    public String saveBankDetails(
            @RequestParam Long userId,
            @RequestParam String accountHolderName,
            @RequestParam String accountNumber,
            @RequestParam String ifscCode) {

        userService.saveBankDetails(
                userId,
                accountHolderName,
                accountNumber,
                ifscCode
        );

        return "Bank details saved successfully";
    }

    // ------------------------------
    // FINAL SUBMIT
    // ------------------------------
    @PostMapping("/homemaker/submit")
    public String submitForReview(@RequestParam Long userId) {

        userService.submitForReview(userId);

        return "Application submitted for admin review";
    }
    
 // ------------------------------
 // GET HOMEMAKER PROFILE
 // ------------------------------
 @GetMapping("/homemaker/profile/{userId}")
 public HomemakerProfile getHomemakerProfile(@PathVariable Long userId) {

     return userService.getHomemakerProfile(userId);

 }
 
 
}