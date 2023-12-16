package com.mindhub.techTreasure.dtos;

import java.util.List;

public class PurchaseOrderApplicationDTO {

    private List<Long> productIds;
    private String shippingAddress;

    public PurchaseOrderApplicationDTO() {
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}
