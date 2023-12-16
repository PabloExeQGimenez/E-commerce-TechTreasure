package com.mindhub.techTreasure.repositories;

import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsBySku(String sku);

    boolean existsByName(String name);

    Product findByName(String name);

    List<Product> findByBrand(ProductBrand brand);

    List<Product> findByCategoryId(Long id);

}
