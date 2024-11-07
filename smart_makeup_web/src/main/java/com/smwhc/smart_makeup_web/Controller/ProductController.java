package com.smwhc.smart_makeup_web.Controller;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Product.Product;
import com.smwhc.smart_makeup_web.Product.ProductService;

@Controller
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 사용자가 메뉴바에서 제품 추천 메뉴를 클릭시 이동
    @GetMapping("/recommendation")
    public String recommendation(Model model) {
        List<Product> products = productService.findByAllProduct();

        List<String> images = new ArrayList<>();
        List<String> productname = new ArrayList<>();
        List<Integer> price = new ArrayList<>();
        List<String> productlink = new ArrayList<>();

        for(Product product : products) {
            productname.add(product.getProduct_name());
            price.add(product.getPrice());
            productlink.add(product.getProduct_link());
            for(Image image : product.getImages()) {
                images.add(image.getImage_link());
                
            }
        }
        
        model.addAttribute("images", images);
        model.addAttribute("productname", productname);
        model.addAttribute("price", price);
        model.addAttribute("productlink", productlink);
        return "recommendation";
    }
}
