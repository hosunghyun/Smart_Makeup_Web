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
        // 제품을 출력하기 위해서 제품 정보를 데이터베이스에서 전부 호출
        List<Product> products = productService.findByAllProduct();

        // 제품 정보는 연관된 테이블이 많아 한 번에 전송하면 전송 중 오류가 발생하므로 나눠서 전송하기 위해 전송하려는 데이터에 대한 리스트 생성
        List<String> images = new ArrayList<>();
        List<String> productname = new ArrayList<>();
        List<Integer> price = new ArrayList<>();
        List<String> productlink = new ArrayList<>();

        // 제품 객체를 forEach로 반북
        for(Product product : products) {
            productname.add(product.getProduct_name());
            price.add(product.getPrice());
            productlink.add(product.getProduct_link());

            // 이미지 링크는 이미지 객체에 있으므로 이미지 객체에 대한 forEach문 반복
            for(Image image : product.getImages()) {
                images.add(image.getImage_link());
                
            }
        }
        
        // 모델 객체를 생성해서 recommendation에 전송
        model.addAttribute("images", images);
        model.addAttribute("productname", productname);
        model.addAttribute("price", price);
        model.addAttribute("productlink", productlink);
        return "recommendation";
    }
}
