package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.OrderDetail;

public class OrderDetailDTO {

    private Long id;
    private Long productId;
    private String productSku;
    private String productName;
    private double productPrice;
    private int quantity;
    private double subtotal;

    public OrderDetailDTO(OrderDetail orderDetail) {
        id=orderDetail.getId();
        productId=orderDetail.getProduct().getId();
        productSku=orderDetail.getProduct().getSku();
        productName=orderDetail.getProduct().getName();
        productPrice=orderDetail.getProduct().getPrice();
        quantity= orderDetail.getQuantity();
        subtotal=orderDetail.getSubtotal();
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductSku() {
        return productSku;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
