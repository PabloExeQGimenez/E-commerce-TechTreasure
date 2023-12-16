package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.Category;
import com.mindhub.techTreasure.models.CategoryName;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO {

    private Long id;

    private CategoryName name;
    private String description;
    private String code;

    private boolean isActive;
    private List<ProductDTO> products;

    public CategoryDTO(Category category) {

        id= category.getId();
        name= category.getName();
        description= category.getDescription();
        code= category.getCode();
        isActive= category.isActive();
        products = category.getProducts().stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());

    }

    public Long getId() {
        return id;
    }

    public CategoryName getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }
}
