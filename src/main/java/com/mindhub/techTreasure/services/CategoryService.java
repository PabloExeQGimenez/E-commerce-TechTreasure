package com.mindhub.techTreasure.services;

import com.mindhub.techTreasure.models.Category;
import com.mindhub.techTreasure.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CategoryService {

    Category findCategoryById(Long id);

    List<Category> findAllCategories();

    boolean existsCategoryByCode(String code);

    void saveCategory(Category category);

}
