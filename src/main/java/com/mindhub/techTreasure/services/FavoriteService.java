package com.mindhub.techTreasure.services;

import com.mindhub.techTreasure.models.Favorite;

public interface FavoriteService {
    void save (Favorite favorite);
    void delete(Favorite favorite);
}
