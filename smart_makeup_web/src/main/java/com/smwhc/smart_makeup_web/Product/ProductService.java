package com.smwhc.smart_makeup_web.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 제품 찾기
    public List<Product> findByAllProduct() {
        List<Product> products = productRepository.findAll();
        return products;
    }
}
