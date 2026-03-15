package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.maybhojan_backend.model.WalletTransaction;
import com.example.maybhojan_backend.repository.DeliveryWalletRepository;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin("http://localhost:5173")
public class DeliveryWalletController {

    @Autowired
    private DeliveryWalletRepository walletRepository;

    // GET WALLET TRANSACTIONS
    @GetMapping("/{partnerId}")
    public List<WalletTransaction> getWallet(@PathVariable Long partnerId) {

        return walletRepository.findByDeliveryPartnerId(partnerId);

    }

}