package com.example.maybhojan_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.service.UserService;
import com.example.maybhojan_backend.dto.IdentityRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    // ------------------------------
    // Register homemaker
    // ------------------------------
    @PostMapping("/homemaker/register")
    public User registerHomemaker(@RequestBody User user) {
        return userService.registerHomemaker(user);
    }

    // ------------------------------
    // Login
    // ------------------------------
    @PostMapping("/users/login")
    public User login(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

    // ------------------------------
    // Identity step
    // ------------------------------
    @PostMapping("/homemaker/identity")
    public String saveIdentity(@RequestBody IdentityRequest request) {

        userService.saveIdentity(request);

        return "Identity saved successfully";
    }

    // ------------------------------
    // Documents step (Cloudinary Upload)
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

        userService.uploadDocuments(userId, govtId, fssai, kitchenPhoto);

        return "Documents uploaded successfully";
    }

    // ------------------------------
    // Bank Step
    // ------------------------------
    @PostMapping("/homemaker/bank")
    public String saveBankDetails(@RequestParam Long userId,
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
    // Submit to Admin
    // ------------------------------
    @PostMapping("/homemaker/submit")
    public String submitForReview(@RequestParam Long userId) {

        userService.submitForReview(userId);

        return "Application submitted for admin review";
    }
}