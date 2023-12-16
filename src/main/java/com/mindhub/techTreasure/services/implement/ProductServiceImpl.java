package com.mindhub.techTreasure.services.implement;

import com.mindhub.techTreasure.models.Product;
import com.mindhub.techTreasure.models.ProductBrand;
import com.mindhub.techTreasure.repositories.ProductRepository;
import com.mindhub.techTreasure.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public boolean existsProductBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

    @Override
    public boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public Product findProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findProductsByBrand(ProductBrand brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> findProductsByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }
}
