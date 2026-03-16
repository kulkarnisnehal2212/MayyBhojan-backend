package com.example.maybhojan_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.example.maybhojan_backend.model.Food;
import com.example.maybhojan_backend.model.User;
import com.example.maybhojan_backend.repository.FoodRepository;
import com.example.maybhojan_backend.repository.UserRepository;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    // ===============================
    // Add Dish
    // ===============================
    public Food addFood(Long userId, String name, Double price, String category,
            String description, MultipartFile image) {

try {

User homemaker = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

if (image == null || image.isEmpty()) {
    throw new RuntimeException("Invalid image file");
}

Map uploadResult = cloudinary.uploader().upload(
        image.getBytes(),
        ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "maybhojan_foods"
        )
);

String imageUrl = uploadResult.get("secure_url").toString();

Food food = new Food();
food.setName(name);
food.setPrice(price);
food.setCategory(category);
food.setDescription(description);
food.setImage(imageUrl);
food.setHomemaker(homemaker);
food.setAvailable(true);

return foodRepository.save(food);

} catch (Exception e) {
throw new RuntimeException("Error adding food: " + e.getMessage());
}
}

    // ===============================
    // Cook Menu
    // ===============================
    public List<Food> getFoodsByHomemaker(Long userId) {

        User homemaker = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return foodRepository.findByHomemaker(homemaker);
    }

    // ===============================
    // Customer Menu
    // ===============================
    public List<Food> getAllAvailableFoods() {
        return foodRepository.findByAvailableTrue();
    }

    // ===============================
    // Delete Dish
    // ===============================
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
}