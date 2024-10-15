package com.smwhc.smart_makeup_web.Product_Makeup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMakeupRepository extends JpaRepository<ProductMakeup, Long>{
    
}