package com.mindhub.techTreasure.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native", strategy = "native")

    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Product product;

    public Favorite(){

    }

    public Favorite(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
