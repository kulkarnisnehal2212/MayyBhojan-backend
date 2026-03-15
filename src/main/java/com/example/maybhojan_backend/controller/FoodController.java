package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maybhojan_backend.model.Food;
import com.example.maybhojan_backend.repository.FoodRepository;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin("http://localhost:5173")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping
    public List<Food> getAllFoods(){
        return foodRepository.findAll();
    }

}
