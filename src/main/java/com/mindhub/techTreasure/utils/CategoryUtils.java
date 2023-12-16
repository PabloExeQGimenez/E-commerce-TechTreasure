package com.mindhub.techTreasure.utils;

import com.mindhub.techTreasure.services.CategoryService;

import java.util.Random;

public final class CategoryUtils {

    private CategoryUtils() {
    }

    public static String generateCode(CategoryService categoryService) {
        Random random = new Random();
        String code;

        do {
            int randomNumber = random.nextInt(10000);
            code = String.format("%04d", randomNumber);

        } while (categoryService.existsCategoryByCode(code));

        return code;
    }
}
