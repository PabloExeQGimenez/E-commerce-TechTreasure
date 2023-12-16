package com.mindhub.techTreasure.controllers;


import com.mindhub.techTreasure.dtos.CustomerApplicationDTO;
import com.mindhub.techTreasure.dtos.CustomerDTO;
import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.Favorite;
import com.mindhub.techTreasure.models.PurchaseOrder;
import com.mindhub.techTreasure.models.Review;
import com.mindhub.techTreasure.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get/customers/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return new CustomerDTO(customerService.findById(id));
    }

    @GetMapping("/get/customers")
    public List<CustomerDTO> getCustomer() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/customer/current")
    public CustomerDTO getCurrentCustomer(Authentication authentication) {
        return new CustomerDTO(customerService.findByEmail(authentication.getName()));
    }

    @GetMapping("/get/customerByEmail")
    public ResponseEntity<Object> getCustomerByEmail(@RequestParam String email, Authentication authentication) {

        if (!authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("You do not have authorization", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(new CustomerDTO(customerService.findByEmail(email)), HttpStatus.OK);
    }

    @PostMapping("/new/customer")
    public ResponseEntity<Object> createCustomer(@RequestParam String name, @RequestParam String lastName, @RequestParam String email, @RequestParam List<String> address, @RequestParam List<String> telephone, @RequestParam String password) {

        if (customerService.exitsByEmail(email)) {
            return new ResponseEntity<>("Email in use", HttpStatus.FORBIDDEN);
        }

        String regexName = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

        if (!name.matches(regexName)) {
            return new ResponseEntity<>("Enter a valid name", HttpStatus.FORBIDDEN);
        }

        if (!lastName.matches(regexName)) {
            return new ResponseEntity<>("Enter a valid lastName", HttpStatus.FORBIDDEN);
        }

        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (!email.matches(regexEmail) || email.isBlank()) {
            return new ResponseEntity<>("Enter a valid email", HttpStatus.FORBIDDEN);
        }

        for (String singleAddress : address) {
            if (singleAddress.isBlank()) {
                return new ResponseEntity<>("Enter a valid address", HttpStatus.FORBIDDEN);
            }
        }

        String regexTelephone = "^\\+?[0-9\\-\\s]+$";
        for (String phoneNumber : telephone) {
            if (!phoneNumber.matches(regexTelephone)) {
                return new ResponseEntity<>("Enter a valid telephone", HttpStatus.FORBIDDEN);
            }
        }

        if (password.isBlank()) {
            return new ResponseEntity<>("Enter a valid password", HttpStatus.FORBIDDEN);
        }

        Customer newCustomer = new Customer(name, lastName, email, address, telephone, passwordEncoder.encode(password));
        customerService.save(newCustomer);

        return new ResponseEntity<>("Successful registration", HttpStatus.OK);
    }

    //admin y client pueden hacer pet a estas rutas
    @PatchMapping("/modify/password/customer")
    public ResponseEntity<?> modifyCustomer(@RequestParam Long customerId, @RequestParam String passwordOld, @RequestParam String passwordNew, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }

        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }
        String passwordBd = customer.getPassword();

        if (!passwordEncoder.matches(passwordOld, passwordBd)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }

        if (passwordNew.isBlank()) {
            return new ResponseEntity<>("Enter a valid password", HttpStatus.FORBIDDEN);
        }

        customer.setPassword(passwordEncoder.encode(passwordNew));
        customerService.save(customer);

        return new ResponseEntity<>("Data updated successfully", HttpStatus.OK);
    }


    @PatchMapping("/modify/name/customer")
    public ResponseEntity<?> modifyNameCustomer(@RequestParam Long customerId, @RequestParam String newName, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }

        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }

        String regexName = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

        if (!newName.matches(regexName)) {
            return new ResponseEntity<>("Enter a valid name", HttpStatus.FORBIDDEN);
        }

        customer.setName(newName);
        customerService.save(customer);

        return new ResponseEntity<>("Data updated successfully", HttpStatus.OK);
    }

    @PatchMapping("/modify/lastName/customer")
    public ResponseEntity<?> modifyLastNameCustomer(@RequestParam Long customerId, @RequestParam String newLastName, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }
        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }

        String regexlastName = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

        if (!newLastName.matches(regexlastName)) {
            return new ResponseEntity<>("Enter a valid lastName", HttpStatus.FORBIDDEN);
        }

        customer.setLastName(newLastName);
        customerService.save(customer);

        return new ResponseEntity<>("Data updated successfully", HttpStatus.OK);
    }

    @PatchMapping("/modify/email/customer")
    public ResponseEntity<?> modifyEmailCustomer(@RequestParam Long customerId, @RequestParam String oldEmail, @RequestParam String newEmail, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName()); //cliente

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }

        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }

        if (!customer.getEmail().equals(oldEmail)) {
            return new ResponseEntity<>("Wrong email", HttpStatus.FORBIDDEN);
        }

        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (!newEmail.matches(regexEmail) || newEmail.isBlank()) {
            return new ResponseEntity<>("Enter a valid email", HttpStatus.FORBIDDEN);
        }

        customer.setEmail(newEmail);
        customerService.save(customer);

        return new ResponseEntity<>("Data updated successfully", HttpStatus.OK);
    }
    @Transactional
    @DeleteMapping("/delete/customer") //enviar el parametro como objeto.
    public ResponseEntity<?> removeCustomer(@RequestParam Long idcustomer, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }
        Customer customer = customerService.findById(idcustomer);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }

        Set<Favorite> favorites = customer.getFavorites();
        Set<Favorite> copyFavorites = new HashSet<>(favorites);

        for (Favorite favorite : copyFavorites) {
            Favorite favoriteBd = favorites.stream().filter(favorite1 -> favorite1.equals(favorite)).findFirst().orElse(null);
            if (favoriteBd != null) {
                favorites.remove(favorite);
                favorite.setCustomer(null);

            }
        }


        Set<Review> reviews = customer.getReviews();
        Set<Review> copyreviews = new HashSet<>(reviews);

        for (Review review : copyreviews) {
            Review reviewBd = reviews.stream().filter(review1 -> review1.equals(review)).findFirst().orElse(null);
            if (reviewBd != null) {
                reviews.remove(review);
                review.setCustomer(null);
            }
        }

        Set<PurchaseOrder> purchaseOrders = customer.getPurchaseOrders();
        Set<PurchaseOrder> copyPurchaseOrder = new HashSet<>(purchaseOrders);

        for (PurchaseOrder purchaseOrder : copyPurchaseOrder) {
            PurchaseOrder purchaseOrderBd = purchaseOrders.stream().filter(purchaseOrder1 -> purchaseOrder1.equals(purchaseOrder)).findFirst().orElse(null);

            if (purchaseOrderBd != null) {
                purchaseOrders.remove(purchaseOrder);
                purchaseOrder.setCustomer(null);
            }
        }
        customerService.deleteCustomer(customer);

        return new ResponseEntity<>("Customer deleted", HttpStatus.OK);
    }

    @PostMapping("/add/telephone")
    public ResponseEntity<?> createTelephone(@RequestParam Long customerId, @RequestParam String newTelephone, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }

        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }
        if (customer.getTelephone().size() >= 3) {
            return new ResponseEntity<>("It is not possible to add another telephone", HttpStatus.FORBIDDEN);
        }

        String regexTelephone = "^\\+?[0-9\\-\\s]+$";
        if (!newTelephone.matches(regexTelephone)) {
            return new ResponseEntity<>("Enter a valid telephone", HttpStatus.FORBIDDEN);
        }

        customer.addTelephone(newTelephone);
        customerService.save(customer);

        return new ResponseEntity<>("Phone successfully added", HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/telephone")
    public ResponseEntity<?> removeTelephone(@RequestParam Long customerId, @RequestParam String telephone, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }
        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }

        if (customer.getTelephone().size() <= 1) {
            return new ResponseEntity<>("You must have at least one telephone", HttpStatus.FORBIDDEN);
        }
        String addressExist = customer.getTelephone().stream().filter(telephone1 -> telephone1.equals(telephone)).findFirst().orElse(null);

        if (addressExist == null) {
            return new ResponseEntity<>("Telephone not found", HttpStatus.FORBIDDEN);
        }

        String regexTelephone = "^\\+?[0-9\\-\\s]+$";
        if (!telephone.matches(regexTelephone)) {
            return new ResponseEntity<>("Enter a valid telephone", HttpStatus.FORBIDDEN);
        }
        customer.removeTelephone(telephone);
        customerService.save(customer);
        return new ResponseEntity<>("Phone successfully deleted", HttpStatus.OK);
    }

    @PostMapping("/add/address")
    public ResponseEntity<?> createAddress(@RequestParam Long customerId, @RequestParam String newAddress, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }

        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }
        if (customer.getAddress().size() >= 3) {
            return new ResponseEntity<>("It is not possible to add another address", HttpStatus.FORBIDDEN);
        }

        customer.addAddresss(newAddress);
        customerService.save(customer);

        return new ResponseEntity<>("Address successfully added", HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/address")
    public ResponseEntity<?> removeAddress(@RequestParam Long customerId, @RequestParam String address, Authentication authentication) {

        Customer customerAuthenticated = customerService.findByEmail(authentication.getName());

        if ((customerAuthenticated == null) && !authentication.getName().contains("@admin")) {
            return new ResponseEntity<>("Please Authenticated", HttpStatus.FORBIDDEN);
        }
        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }

        if (customer.getAddress().size() <= 1) {
            return new ResponseEntity<>("You must have at least one address", HttpStatus.FORBIDDEN);
        }

        String addressExist = customer.getAddress().stream().filter(address1 -> address1.equals(address)).findFirst().orElse(null);

        if (addressExist == null) {
            return new ResponseEntity<>("Address not found", HttpStatus.FORBIDDEN);
        }

        customer.removeAddresss(address);
        customerService.save(customer);
        return new ResponseEntity<>("Address successfully deleted", HttpStatus.OK);
    }
}