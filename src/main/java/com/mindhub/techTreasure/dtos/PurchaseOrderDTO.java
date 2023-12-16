package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.OrderStatus;
import com.mindhub.techTreasure.models.PurchaseOrder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class PurchaseOrderDTO {
    private Long id;
    private String number;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Double tax;
    private Double total;
    private String shippingAddress;

    public PurchaseOrderDTO(){

    }
    public PurchaseOrderDTO (PurchaseOrder purchaseOrder){
     this.id= purchaseOrder.getId();
     this.number= purchaseOrder.getNumber();
     this.creationDate=purchaseOrder.getCreationDate();
     this.status=purchaseOrder.getStatus();
     this.tax= purchaseOrder.getTax();
     this.total= purchaseOrder.getTotal();
     this.shippingAddress=purchaseOrder.getShippingAddress();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Double getTax() {
        return tax;
    }

    public Double getTotal() {
        return total;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}
