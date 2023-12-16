package com.mindhub.techTreasure.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "category")
    @GenericGenerator(name="category",strategy = "native")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryName name;
    private String description;
    private String code;
    private boolean isActive;
    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<Product> products= new HashSet<>();

    public Category() {
    }

    public Category(CategoryName name, String description, String code, boolean isActive) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public CategoryName getName() {
        return name;
    }

    public void setName(CategoryName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
