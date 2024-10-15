package com.smwhc.smart_makeup_web.Product_Makeup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smwhc.smart_makeup_web.Product.ProductRepository;

@Service
public class ProductMakeupService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductMakeupService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. 게시판 작성

    // 2. 게시판 수정

    // 3. 게시판 삭제

    // 4. 게시판 보기
}
