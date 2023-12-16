package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.ProductBrand;

public class ProductApplicationDTO {
    private String name;
    private double price;
    private int stock;
    private ProductBrand brand;
    private String description;
    private boolean isActive;
    private String imageURL;
    private Long categoryId;

    public ProductApplicationDTO() {
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public ProductBrand getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getImageURL() {
        return imageURL;
    }
    public void setBrand(String brand) {
        this.brand = (brand != null && !brand.isEmpty()) ? ProductBrand.valueOf(brand) : null;
    }
}
