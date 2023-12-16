package com.mindhub.techTreasure.services.implement;

import com.mindhub.techTreasure.models.PurchaseOrder;
import com.mindhub.techTreasure.repositories.PurchaseOrderRepository;
import com.mindhub.techTreasure.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Override
    public boolean existsByNumberOrder(String number) {
        return purchaseOrderRepository.existsByNumber(number);
    }

    @Override
    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
    purchaseOrderRepository.save(purchaseOrder);
    }
}
