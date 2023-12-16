package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.models.Favorite;
import com.mindhub.techTreasure.models.Product;

public class FavoriteDTO {

    private Long id;
    private Customer customer;
    private Product product;

    public FavoriteDTO(){

    }

    public FavoriteDTO(Favorite favorite) {
        this.id = favorite.getId();
        this.customer = favorite.getCustomer();
        this.product = favorite.getProduct();
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }
}
