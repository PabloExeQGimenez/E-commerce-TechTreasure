package com.mindhub.techTreasure.services.implement;

import com.mindhub.techTreasure.models.Category;
import com.mindhub.techTreasure.repositories.CategoryRepository;
import com.mindhub.techTreasure.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean existsCategoryByCode(String code) {
        return categoryRepository.existsByCode(code);
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
