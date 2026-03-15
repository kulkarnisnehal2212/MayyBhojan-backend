package com.example.maybhojan_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.model.HomemakerProfile;
import com.example.maybhojan_backend.repository.UserRepository;
import com.example.maybhojan_backend.repository.HomemakerProfileRepository;
import com.example.maybhojan_backend.service.AdminService;
import com.example.maybhojan_backend.dto.AdminHomemakerDetailsDTO;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomemakerProfileRepository homemakerProfileRepository;

    @Autowired
    private AdminService adminService;

    // ----------------------------
    // View pending homemakers
    // ----------------------------
    @GetMapping("/homemakers/pending")
    public List<User> getPendingHomemakers() {

        return userRepository.findByRoleAndAccountStatus(
                "HOMEMAKER",
                "UNDER_REVIEW"
        );
    }

    // ----------------------------
    // Get full homemaker details
    // ----------------------------
    @GetMapping("/homemaker/{id}/details")
    public AdminHomemakerDetailsDTO getHomemakerDetails(@PathVariable Long id) {

        return adminService.getHomemakerDetails(id);

    }

    // ----------------------------
    // Approve homemaker
    // ----------------------------
    @PutMapping("/homemaker/{id}/approve")
    public String approveHomemaker(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountStatus("ACTIVE");

        userRepository.save(user);

        // Activate kitchen
        HomemakerProfile profile = homemakerProfileRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setIsAvailable(true);

        homemakerProfileRepository.save(profile);

        return "Homemaker approved successfully";
    }

    // ----------------------------
    // Reject homemaker
    // ----------------------------
    @PutMapping("/homemaker/{id}/reject")
    public String rejectHomemaker(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountStatus("REJECTED");

        userRepository.save(user);

        return "Homemaker rejected";
    }
}