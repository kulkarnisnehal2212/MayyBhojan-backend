package com.example.maybhojan_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maybhojan_backend.model.Favorite;
import com.example.maybhojan_backend.repository.FavoriteRepository;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getFavorites(Long userId){
        return favoriteRepository.findByUserId(userId);
    }

    public Favorite addFavorite(Favorite favorite){
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long id){
        favoriteRepository.deleteById(id);
    }

}
