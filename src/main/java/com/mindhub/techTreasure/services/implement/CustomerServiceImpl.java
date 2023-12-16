package com.mindhub.techTreasure.services.implement;

import ch.qos.logback.core.net.server.Client;
import com.mindhub.techTreasure.dtos.CustomerDTO;
import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.Review;
import com.mindhub.techTreasure.repositories.CustomerRepository;
import com.mindhub.techTreasure.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean exitsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> new CustomerDTO(customer)).collect(Collectors.toList());
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

}
