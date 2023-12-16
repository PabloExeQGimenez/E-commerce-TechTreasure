package com.mindhub.techTreasure.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product")
    @GenericGenerator(name="product",strategy = "native")
    private Long id;
    private String sku;
    private String name;
    private double price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private ProductBrand brand;
    @Column(length = 1000)
    private String description;
    private String imageURL;
    private boolean isActive;
    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Review> reviews= new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private Set<Favorite> favorites= new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails= new HashSet<>();
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

    public Product() {
    }

    public Product(String sku, String name, double price, int stock, ProductBrand brand, String description, String imageURL, boolean isActive) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.description = description;
        this.imageURL = imageURL;
        this.isActive= isActive;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ProductBrand getBrand() {
        return brand;
    }

    public void setBrand(ProductBrand brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Set<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addReview(Review review){
        review.setProduct(this);
        reviews.add(review);
    }

    public void deleteReview(Review review){
        reviews.remove(review);
        review.setProduct(null);
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setProduct(this);
        orderDetails.add(orderDetail);
    }

    public void addFavorite(Favorite favorite){
        favorite.setProduct(this);
        favorites.add(favorite);
    }

    public void removeFavorite(Favorite favorite){
        favorites.remove(favorite);
        favorite.setProduct(null);
    }

    public void addCategory(Category category){
        this.category = category;
    }
}
