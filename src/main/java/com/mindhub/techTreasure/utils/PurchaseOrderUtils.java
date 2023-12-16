package com.mindhub.techTreasure.utils;

import com.mindhub.techTreasure.services.PurchaseOrderService;

public final class PurchaseOrderUtils {

    private PurchaseOrderUtils(){

    }
    public static int getRandomNumber(int min, int max) {return (int) ((Math.random() * (max - min)) + min);}

    public static String generateNumberOrder(PurchaseOrderService purchaseOrderService) {
        StringBuilder OrderNumber;
        do {
            OrderNumber = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                OrderNumber.append(getRandomNumber(0, 9));
            }
        } while (purchaseOrderService.existsByNumberOrder(OrderNumber.toString()));
        return OrderNumber.toString();
    }
}
