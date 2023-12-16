package com.mindhub.techTreasure.controllers;

import com.mindhub.techTreasure.dtos.CategoryDTO;
import com.mindhub.techTreasure.models.Category;
import com.mindhub.techTreasure.models.CategoryName;
import com.mindhub.techTreasure.services.CategoryService;
import com.mindhub.techTreasure.utils.CategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public List<CategoryDTO> getCategories() {
        return categoryService.findAllCategories().stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    }

    /*@PostMapping("/categories/create")
    public ResponseEntity<String> createCategory(@RequestParam CategoryName name, @RequestParam String description) {


        if (description.isBlank()) {
            return new ResponseEntity<>("The name field cannot be empty or have blank spaces.", HttpStatus.FORBIDDEN);
        }

        if (name==null || !EnumSet.allOf(CategoryName.class).contains(name)) {
            return new ResponseEntity<>("invalid value for category name.", HttpStatus.FORBIDDEN);
        }

        Category category = new Category(name, description, CategoryUtils.generateCode(categoryService), true);
        categoryService.saveCategory(category);
        return new ResponseEntity<>("Category successfully created.", HttpStatus.CREATED);
    }*/

    @PostMapping("/categories/{id}/update")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestParam String description, Authentication authentication) {

        /*String user= authentication.getName();

        if(!user.contains("admin@")){
            return new ResponseEntity<>("You do not have sufficient permissions", HttpStatus.FORBIDDEN);
        }*/

        Category category = categoryService.findCategoryById(id);

        if (category == null) {
            return new ResponseEntity<>("Category not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        if (description == null || description.isBlank()) {
            return new ResponseEntity<>("Enter a valid value for description",HttpStatus.FORBIDDEN);
        }

        category.setDescription(description);
        categoryService.saveCategory(category);
        return new ResponseEntity<>("Category successfully updated.", HttpStatus.OK);

    }

    @PatchMapping("/categories/{id}/deactivate")
    public ResponseEntity<String> deactivateCategory(@PathVariable Long id, @RequestParam boolean isActive, Authentication authentication) {

        /*String user= authentication.getName();

        if(!user.contains("admin@")){
            return new ResponseEntity<>("You do not have sufficient permissions", HttpStatus.FORBIDDEN);
        }*/

        Category category = categoryService.findCategoryById(id);

        if (category == null) {
            return new ResponseEntity<>("Category not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        category.setActive(isActive);
        categoryService.saveCategory(category);

        if(isActive==true){
            return new ResponseEntity<>("Category successfully activate.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category successfully deactivate.", HttpStatus.OK);
    }

}
