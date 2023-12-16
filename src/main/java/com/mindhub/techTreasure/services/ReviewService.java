package com.mindhub.techTreasure.services;

import com.mindhub.techTreasure.models.Favorite;
import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.Review;

public interface ReviewService {

    void save (Review review);

    Review findById(Long id);
    void delete(Review review);

//    Review findbyIdAntProductReview(Long id, Product product);
}
