package com.smwhc.smart_makeup_web.Product_Category;

import java.util.Set;

import com.smwhc.smart_makeup_web.Makeup.MakeUp;
import com.smwhc.smart_makeup_web.Product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_category")
@Getter
@Setter
public class ProductCategory {
    @Id
    @Column(name = "category", updatable = false, length = 50)
    private String category;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> product;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<MakeUp> makeup;

    // 생성자
    public ProductCategory() {}
    public ProductCategory(String category) {
        this.category = category;
    }
}
