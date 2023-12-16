package com.mindhub.techTreasure.services.implement;

import com.mindhub.techTreasure.models.Favorite;
import com.mindhub.techTreasure.repositories.FavoriteRepository;
import com.mindhub.techTreasure.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Override
    public void save(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    @Override
    public void delete(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }
}
