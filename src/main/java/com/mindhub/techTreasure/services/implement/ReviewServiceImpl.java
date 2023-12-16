package com.mindhub.techTreasure.services.implement;

import com.mindhub.techTreasure.models.Favorite;
import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.Review;
import com.mindhub.techTreasure.repositories.ReviewRepository;
import com.mindhub.techTreasure.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Override
    public void save(Review review) {
       reviewRepository.save(review);
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }

//    @Override
//    public Review findbyIdAntProductReview(Long id, Product product) {
//        return reviewRepository.findByIdAndProduct(id,product);
//    }
}
