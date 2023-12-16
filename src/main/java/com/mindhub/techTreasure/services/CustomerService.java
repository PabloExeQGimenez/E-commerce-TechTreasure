package com.mindhub.techTreasure.services;

import ch.qos.logback.core.net.server.Client;
import com.mindhub.techTreasure.dtos.CustomerDTO;
import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.Review;

import java.util.List;

public interface CustomerService {
    Customer findByEmail(String email);

    void save(Customer customer);

    boolean exitsByEmail(String email);

    List<CustomerDTO> findAllCustomers();

    Customer findById(Long id);

    void deleteCustomer(Customer customer);

//   Review findByReviewAndClientReview(Long idReview, Client client);
}
