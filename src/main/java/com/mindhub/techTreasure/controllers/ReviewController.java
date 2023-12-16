package com.mindhub.techTreasure.controllers;

import ch.qos.logback.core.net.server.Client;
import com.mindhub.techTreasure.dtos.ProductDTO;
import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.Favorite;
import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.Review;
import com.mindhub.techTreasure.repositories.ProductRepository;
import com.mindhub.techTreasure.services.CustomerService;
import com.mindhub.techTreasure.services.ProductService;
import com.mindhub.techTreasure.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReviewService reviewService;


    @PostMapping("/new/review")
    public ResponseEntity<Object> addReview(@RequestParam Long productId, @RequestParam String comment, @RequestParam Integer rating, Authentication authentication) {

        Customer customer = customerService.findByEmail(authentication.getName());
        Product product = productService.findProductById(productId);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }
        if (product == null) {
            return new ResponseEntity<>("Indicate the product to add the review", HttpStatus.FORBIDDEN);
        }
        Review review = customer.getReviews().stream().filter(review1 -> review1.getProduct().equals(product)).findFirst().orElse(null);
//        busca dentro de los productos comprados, que coincida con el producto al que se le quiere hacer la reseña

        if (review == null) {
            return new ResponseEntity<>("It is not possible to make a review", HttpStatus.FORBIDDEN);
        }

        String regexComment = "^[a-zA-Z]+(([',. -][a-zA-Z])?[a-zA-Z]*)*$";
        if (comment.isBlank() || !comment.matches(regexComment)) {
            return new ResponseEntity<>("Enter a valid comment", HttpStatus.FORBIDDEN);
        }

        if (rating < 1 || rating > 5) {
            return new ResponseEntity<>("Please enter a valid rating", HttpStatus.FORBIDDEN);
        }

        if(review.getComment()!=null ) {
            review.setComment(review.getComment() + "\n" + "EDIT: " + comment);
            review.setRating(rating);
            reviewService.save(review);
            return new ResponseEntity<>("Review edited successfully", HttpStatus.OK);
        }

        review.setComment(comment);
        review.setRating(rating);
        reviewService.save(review);

        return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
    }

    @PatchMapping("/modify/review")
    public ResponseEntity<?> modifyReview(@RequestParam Long reviewId,  Authentication authentication){

        if(!authentication.getName().contains("@admin")){
            return new ResponseEntity<>("Please authenticate",HttpStatus.FORBIDDEN);
        }

        Review review= reviewService.findById(reviewId);

        if(review==null){
            return new ResponseEntity<>("Review not found",HttpStatus.FORBIDDEN);
        }

        Product product= review.getProduct();
        Customer customer= review.getCustomer();

        if(product==null || customer ==null){
            return new ResponseEntity<>("Review not found",HttpStatus.FORBIDDEN);
        }

        review.setComment(null);
        review.setRating(null);
        reviewService.save(review);


        return  new ResponseEntity<>("Review successfully deleted",HttpStatus.OK);
    }
}
