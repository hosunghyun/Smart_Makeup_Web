package com.smwhc.smart_makeup_web.Product_Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {
    @Autowired
    private final ProductTypeService productTypeService;

    public ProductTypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    // 1. 게시판 작성

    // 2. 게시판 수정

    // 3. 게시판 삭제

    // 4. 게시판 보기
}
