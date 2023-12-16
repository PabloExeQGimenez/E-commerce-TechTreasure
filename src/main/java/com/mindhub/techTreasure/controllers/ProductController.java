package com.mindhub.techTreasure.controllers;

import com.mindhub.techTreasure.dtos.ProductApplicationDTO;
import com.mindhub.techTreasure.dtos.ProductDTO;
import com.mindhub.techTreasure.dtos.ReviewDTO;
import com.mindhub.techTreasure.models.Category;
import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.ProductBrand;
import com.mindhub.techTreasure.services.CategoryService;
import com.mindhub.techTreasure.services.ProductService;
import com.mindhub.techTreasure.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public List<ProductDTO> getProducts(){
        return productService.findAllProducts().stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable Long id){
        Product product = productService.findProductById(id);
        return new ProductDTO(product);
    }

    @PostMapping("/products/create")
    public ResponseEntity<String> createProduct(@RequestBody ProductApplicationDTO productApplicationDTO, Authentication authentication){

        /*String user= authentication.getName();

        if(!user.contains("admin@")){
            return new ResponseEntity<>("You do not have sufficient permissions", HttpStatus.FORBIDDEN);
        }*/

        if(productApplicationDTO.getName().isBlank()){
            return new ResponseEntity<>("The name field cannot be empty or have blank spaces.", HttpStatus.FORBIDDEN);
        }

        if(productService.existsProductByName(productApplicationDTO.getName())){
            return new ResponseEntity<>("There is already a product with that name, please enter a new one.", HttpStatus.FORBIDDEN);
        }

        if(productApplicationDTO.getPrice()<=0){
            return new ResponseEntity<>("The price of the product cannot be less than or equal to zero.", HttpStatus.FORBIDDEN);
        }

        if(productApplicationDTO.getStock()<=0){
            return new ResponseEntity<>("The stock cannot be less than or equal to zero.", HttpStatus.FORBIDDEN);
        }

        if(productApplicationDTO.getBrand()==null || !EnumSet.allOf(ProductBrand.class).contains(productApplicationDTO.getBrand())){
            return new ResponseEntity<>("To create an product, you need to send a valid product brand.", HttpStatus.FORBIDDEN);
        }

        if(productApplicationDTO.getDescription().isBlank()){
            return new ResponseEntity<>("The description field cannot be empty or have blank spaces.", HttpStatus.FORBIDDEN);
        }

        if (productApplicationDTO.getImageURL() == null || productApplicationDTO.getImageURL().isBlank()) {
            return new ResponseEntity<>("The imageURL field cannot be empty or have blank spaces.", HttpStatus.FORBIDDEN);
        }

        Product product= new Product(ProductUtils.generateSKU(productService), productApplicationDTO.getName(), productApplicationDTO.getPrice(), productApplicationDTO.getStock(), productApplicationDTO.getBrand(), productApplicationDTO.getDescription(), productApplicationDTO.getImageURL(), true);

        if(productApplicationDTO.getCategoryId()==null){
            return new ResponseEntity<>("Enter a valid category value", HttpStatus.FORBIDDEN);
        }

        Category category = categoryService.findCategoryById(productApplicationDTO.getCategoryId());

        product.addCategory(category);
        productService.saveProduct(product);

        return new ResponseEntity<>("Successfully created product",HttpStatus.CREATED);
    }

    @PostMapping("/products/{id}/update")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductApplicationDTO productApplicationDTO, Authentication authentication){

        /*String user= authentication.getName();

        if(!user.contains("admin@")){
            return new ResponseEntity<>("You do not have sufficient permissions", HttpStatus.FORBIDDEN);
        }*/

        Product product= productService.findProductById(id);

        if (product == null) {
            return new ResponseEntity<>("There is no product with that id", HttpStatus.NOT_FOUND);
        }

        if (productApplicationDTO.getName() != null && !productApplicationDTO.getName().isBlank()) {
            product.setName(productApplicationDTO.getName());
        }

        if (productApplicationDTO.getPrice() > 0) {
            product.setPrice(productApplicationDTO.getPrice());
        }

        if (productApplicationDTO.getStock() >= 0) {
            product.setStock(productApplicationDTO.getStock());
        }

        if (productApplicationDTO.getBrand() != null) {
            product.setBrand(productApplicationDTO.getBrand());
        }

        if (productApplicationDTO.getDescription() != null && !productApplicationDTO.getDescription().isBlank()) {
            product.setDescription(productApplicationDTO.getDescription());
        }

        if(productApplicationDTO.getImageURL() != null && !productApplicationDTO.getImageURL().isBlank()){
            product.setImageURL(productApplicationDTO.getImageURL());
        }

        if (productApplicationDTO.getCategoryId() != null) {
            Category category = categoryService.findCategoryById(productApplicationDTO.getCategoryId());

            if (category != null) {
                product.setCategory(category);
            } else {
                return new ResponseEntity<>("Category not found with id: " + productApplicationDTO.getCategoryId(), HttpStatus.NOT_FOUND);
            }
        }

        productService.saveProduct(product);

        return new ResponseEntity<>("Product successfully updated.",HttpStatus.OK);
    }

    @PatchMapping("/products/{id}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long id, @RequestParam boolean isActive, Authentication authentication) {

        /*String user = authentication.getName();

        if (!user.contains("admin@")) {
            return new ResponseEntity<>("You do not have sufficient permissions", HttpStatus.FORBIDDEN);
        }*/

        Product product = productService.findProductById(id);

        if (product == null) {
            return new ResponseEntity<>("There is no product with that id", HttpStatus.NOT_FOUND);
        }

        product.setActive(isActive);
        productService.saveProduct(product);

        if(isActive==true){
            return new ResponseEntity<>("Product successfully activate.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product successfully deactivate.", HttpStatus.OK);
    }

    @GetMapping("/brands")
    public List<ProductBrand> getBrands() {
        return EnumSet.allOf(ProductBrand.class).stream().collect(Collectors.toList());
    }

    @GetMapping("/brands/{brand}/products")
    public List<ProductDTO> getProductsByBrand(@PathVariable ProductBrand brand) {
        return productService.findProductsByBrand(brand)
                .stream().map(product -> new ProductDTO(product))
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{categoryId}/products")
    public List<ProductDTO> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.findProductsByCategoryId(categoryId)
                .stream().map(product -> new ProductDTO(product))
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<Object> getProductReviews(@PathVariable Long productId) {
        Product product = productService.findProductById(productId);

        if (product == null) {
            return new ResponseEntity<>("The product was not found.",HttpStatus.NOT_FOUND);
        }

        List<ReviewDTO> reviewDTOs = product.getReviews().stream()
                .map(review -> new ReviewDTO(review))
                .collect(Collectors.toList());

        if (reviewDTOs.isEmpty()) {
            return new ResponseEntity<>("No reviews found for the product.", HttpStatus.OK);
        }

        return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
    }
}
