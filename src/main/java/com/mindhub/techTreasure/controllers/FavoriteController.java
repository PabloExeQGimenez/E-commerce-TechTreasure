package com.mindhub.techTreasure.controllers;

import com.mindhub.techTreasure.dtos.ProductDTO;
import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.Favorite;
import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.repositories.ProductRepository;
import com.mindhub.techTreasure.services.CustomerService;
import com.mindhub.techTreasure.services.FavoriteService;
import com.mindhub.techTreasure.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/current/favorites")
    public Set<ProductDTO> getFavs (Authentication authentication){

        Customer customer= customerService.findByEmail(authentication.getName());

        return customer.getFavorites().stream().map(favorite -> favorite.getProduct()).map(product -> new ProductDTO(product)).collect(Collectors.toSet());
    }

    @PostMapping("/new/favorite")
    public ResponseEntity<?> addFavorite (@RequestParam Long productId, Authentication authentication){

        Customer customer = customerService.findByEmail(authentication.getName());

        Product product = productService.findProductById(productId);

        if(customer == null){
            return new ResponseEntity<>("Customer must be authenticated to add favorites", HttpStatus.FORBIDDEN);
        }

        if(product == null){
            return new ResponseEntity<>("Product not found",HttpStatus.FORBIDDEN);
        }
        if(customer.getFavorites().stream().anyMatch(favorite -> favorite.getProduct().equals(product))){
            return new ResponseEntity<>("Product is already in favorites",HttpStatus.FORBIDDEN);
        }

        Favorite favorite = new Favorite(customer,product);
        customer.addFavorite(favorite);
        product.addFavorite(favorite);
        favoriteService.save(favorite);
        customerService.save(customer);
        productService.saveProduct(product);

        return new ResponseEntity<>("Added to favorites",HttpStatus.OK);
    }

    @DeleteMapping("/delete/favorite")
    public ResponseEntity<?> removeFavorite(@RequestParam Long productId,Authentication authentication){

        Customer customer = customerService.findByEmail(authentication.getName());

        Product product = productService.findProductById(productId);

        if(customer != null && product != null){
            Favorite existingFavorite= customer.getFavorites().stream()
                    .filter(favorite -> favorite.getProduct().equals(product))
                    .findFirst().orElse(null);

            if(existingFavorite != null){
                customer.removeFavorite(existingFavorite);
                product.removeFavorite(existingFavorite);
                favoriteService.delete(existingFavorite);
                customerService.save(customer);
                productService.saveProduct(product);
                return new ResponseEntity<>("Deleted of favorites",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Product not found in favorites",HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("Could not be deleted",HttpStatus.FORBIDDEN);
    }
}
