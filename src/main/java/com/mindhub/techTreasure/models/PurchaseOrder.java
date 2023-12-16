package com.mindhub.techTreasure.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order")
    @GenericGenerator(name ="order", strategy = "native")

    private Long id;
    private String number;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Double tax;
    private Double total;
    private String shippingAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Customer customer;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    public PurchaseOrder(){

    }
    public PurchaseOrder(String number, LocalDateTime creationDate, OrderStatus status, Double tax, Double total, String shippingAddress){
        this.number=number;
        this.creationDate=creationDate;
        this.status=status;
        this.tax=tax;
        this.total=total;
        this.shippingAddress=shippingAddress;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderDetail> getOrderDetail() {
        return orderDetails;
    }

    public void setOrderDetail(Set<OrderDetail> orderDetail) {
        this.orderDetails = orderDetail;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setPurchaseOrder(this);
        orderDetails.add(orderDetail);
    }

    public void deleteOrderDetail(OrderDetail orderDetail){
       orderDetails.remove(orderDetail);
       orderDetail.setPurchaseOrder(null);
    }
}
