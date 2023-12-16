package com.mindhub.techTreasure.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @Column(length = 1000)
    private String comment;
    private Integer rating;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Product product;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Customer customer;

    public Review() {

    }

    public Review(String comment, Integer rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
