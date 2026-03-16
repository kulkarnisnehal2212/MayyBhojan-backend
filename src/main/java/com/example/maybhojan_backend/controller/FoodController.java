
package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.maybhojan_backend.model.Food;
import com.example.maybhojan_backend.service.FoodService;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin("http://localhost:5173")
public class FoodController {

    @Autowired
    private FoodService foodService;

    // Customer menu
    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllAvailableFoods();
    }

    // Cook menu
    @GetMapping("/cook/{userId}")
    public List<Food> getCookFoods(@PathVariable Long userId) {
        return foodService.getFoodsByHomemaker(userId);
    }

    // Add dish
    @PostMapping
    public Food addFood(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam MultipartFile image) {

        return foodService.addFood(userId, name, price, category, description, image);
    }

    // Delete dish
    @DeleteMapping("/{id}")
    public String deleteFood(@PathVariable Long id) {

        foodService.deleteFood(id);

        return "Food deleted successfully";
    }
}
