package com.mindhub.techTreasure.services;

import com.mindhub.techTreasure.models.PurchaseOrder;
import org.springframework.stereotype.Service;


public interface PurchaseOrderService {

    boolean existsByNumberOrder(String number);

    void savePurchaseOrder(PurchaseOrder purchaseOrder);
}
