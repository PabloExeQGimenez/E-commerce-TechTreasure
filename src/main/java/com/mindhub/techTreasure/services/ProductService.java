package com.mindhub.techTreasure.services;

import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.ProductBrand;

import java.util.List;

public interface ProductService {

    void saveProduct(Product product);

    Product findProductById(Long id);

    List<Product> findAllProducts();

    boolean existsProductBySku(String sku);

    boolean existsProductByName(String name);

    Product findProductByName(String name);

    List<Product> findProductsByBrand(ProductBrand brand);

    List<Product> findProductsByCategoryId(Long id);

}
