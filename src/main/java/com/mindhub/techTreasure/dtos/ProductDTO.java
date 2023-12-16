package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.ProductBrand;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {

    private Long id;
    private String sku;
    private String name;
    private double price;
    private int stock;
    private ProductBrand brand;
    private String description;
    private String imageURL;
    private boolean isActive;
    private Long categoryId;
    private List<FavoriteDTO> favorites;
    private List<ReviewDTO> reviews;
    private List<OrderDetailDTO> orderDetails;

    public ProductDTO(Product product) {
        id=product.getId();
        sku= product.getSku();
        name= product.getName();
        price= product.getPrice();
        stock= product.getStock();
        brand=product.getBrand();
        description= product.getDescription();
        imageURL= product.getImageURL();
        isActive= product.isActive();
        categoryId = product.getCategory().getId();
        favorites=product.getFavorites().stream().map(favorite -> new FavoriteDTO(favorite)).collect(Collectors.toList());
        reviews=product.getReviews().stream().map(review -> new ReviewDTO(review)).collect(Collectors.toList());
        orderDetails=product.getOrderDetails().stream().map(orderDetail -> new OrderDetailDTO(orderDetail)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
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

    public boolean isActive() {
        return isActive;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public List<FavoriteDTO> getFavorites() {
        return favorites;
    }
}
