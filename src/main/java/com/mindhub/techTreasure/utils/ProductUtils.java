package com.mindhub.techTreasure.utils;

import com.mindhub.techTreasure.services.ProductService;

import java.util.Random;

public final class ProductUtils {

    private ProductUtils() {
    }

    public static String generateSKU(ProductService productService) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int length=10;
        String sku;

        do {
            StringBuilder skuBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(characters.length());
                skuBuilder.append(characters.charAt(randomIndex));
            }

            sku = skuBuilder.toString();
        } while (productService.existsProductBySku(sku));

        return sku;
    }

}
