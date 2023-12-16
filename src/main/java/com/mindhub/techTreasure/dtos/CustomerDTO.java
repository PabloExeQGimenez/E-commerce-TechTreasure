package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.PurchaseOrder;
import com.mindhub.techTreasure.models.Review;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private List<String> address;
    private List<String> telephone;
    private Set<PurchaseOrderDTO> purchaseOrders;
    private Set<ReviewDTO> reviews;
    private Set<ProductDTO> purchasedProducts;
    private Set<FavoriteDTO> favorites;

    public CustomerDTO(){

    }

    public CustomerDTO(Customer customer){
        this.id=customer.getId();
        this.name= customer.getName();
        this.email= customer.getEmail();
        this.lastName= customer.getLastName();
        this.address= customer.getAddress();
        this.telephone= customer.getTelephone();
        this.purchaseOrders=customer.getPurchaseOrders().stream().map(PurchaseOrderDTO::new).collect(Collectors.toSet());
        this.reviews=customer.getReviews().stream().map(ReviewDTO::new).collect(Collectors.toSet());
        this.purchasedProducts=customer.getReviews().stream().map(Review::getProduct).map(ProductDTO::new).collect(Collectors.toSet());
        this.favorites= customer.getFavorites().stream().map(FavoriteDTO::new).collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getAddress() {
        return address;
    }

    public List<String> getTelephone() {
        return telephone;
    }

    public Set<PurchaseOrderDTO> getPurchaseOrders() {
        return purchaseOrders;
    }

    public Set<ReviewDTO> getReviews() {
        return reviews;
    }

    public Set<ProductDTO> getPurchasedProducts() {
        return purchasedProducts;
    }

    public Set<FavoriteDTO> getFavorites() {
        return favorites;
    }
}
