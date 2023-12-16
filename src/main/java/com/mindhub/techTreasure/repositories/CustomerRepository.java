package com.mindhub.techTreasure.repositories;

import ch.qos.logback.core.net.server.Client;
import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
@Repository
public interface CustomerRepository extends JpaRepository<Customer ,Long> {

    Customer findByEmail(String email);

//    Review findByReviewAndClient(Long idReview, Client client);
    boolean existsByEmail(String email);
}
