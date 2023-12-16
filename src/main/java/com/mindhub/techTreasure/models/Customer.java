package com.mindhub.techTreasure.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name, lastName, email, password;
    @ElementCollection
    private List<String> address = new ArrayList<>();

    @ElementCollection
    private List<String> telephone = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Favorite> favorites = new HashSet<>();

    public Customer() {

    }

    public Customer(String name, String lastName, String email, List<String> address, List<String> telephone, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<String> getTelephone() {
        return telephone;
    }

    public void setTelephone(List<String> telephone) {
        this.telephone = telephone;
    }
    @JsonIgnore
    public Set<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
    @JsonIgnore
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @JsonIgnore
    public Set<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }

    public void addOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setCustomer(this);
        purchaseOrders.add(purchaseOrder);
    }

    public void addReview(Review review) {
        review.setCustomer(this);
        reviews.add(review);
    }

    public void deleteReview(Review review) {
        reviews.remove(review);
        review.setCustomer(null);
    }

    public void addFavorite(Favorite favorite) {
        favorite.setCustomer(this);
        favorites.add(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        favorites.remove(favorite);
        favorite.setCustomer(null);
    }

    public void addAddresss(String stringAddress) {
        address.add(stringAddress);
    }

    public void removeAddresss(String stringAddress) {
        address.remove(stringAddress);
    }

    public void addTelephone(String stringTelephone) {
        telephone.add(stringTelephone);
    }

    public void removeTelephone(String stringTelephone) {
        telephone.remove(stringTelephone);
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", telephone=" + telephone +
                ", purchaseOrders=" + purchaseOrders +
                ", reviews=" + reviews +
                ", favorites=" + favorites +
                '}';
    }
}
