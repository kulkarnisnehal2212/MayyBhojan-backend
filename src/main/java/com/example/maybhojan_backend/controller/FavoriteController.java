package com.example.maybhojan_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maybhojan_backend.model.Favorite;
import com.example.maybhojan_backend.service.FavoriteService;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin("http://localhost:5173")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/{userId}")
    public List<Favorite> getFavorites(@PathVariable Long userId){
        return favoriteService.getFavorites(userId);
    }

    @PostMapping
    public Favorite addFavorite(@RequestBody Favorite favorite){
        return favoriteService.addFavorite(favorite);
    }

    @DeleteMapping("/{id}")
    public void removeFavorite(@PathVariable Long id){
        favoriteService.removeFavorite(id);
    }

}