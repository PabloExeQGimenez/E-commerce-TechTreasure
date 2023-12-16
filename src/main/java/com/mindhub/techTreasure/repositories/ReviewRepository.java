package com.mindhub.techTreasure.repositories;

import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

//    Review findByIdAndProduct(Long id, Product product);
}
