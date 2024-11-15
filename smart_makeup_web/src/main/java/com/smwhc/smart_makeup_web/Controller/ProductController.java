package com.smwhc.smart_makeup_web.Controller;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Makeup.MakeUp;
import com.smwhc.smart_makeup_web.Makeup.MakeUpService;
import com.smwhc.smart_makeup_web.Member.Member;
import com.smwhc.smart_makeup_web.Member.MemberService;
import com.smwhc.smart_makeup_web.Product.Product;
import com.smwhc.smart_makeup_web.Product.ProductService;

@Controller
public class ProductController {
    @Autowired
    private final ProductService productService;
    private final MakeUpService makeUpService;
    private final MemberService memberService;

    public ProductController(ProductService productService, MakeUpService makeUpService, MemberService memberService) {
        this.productService = productService;
        this.makeUpService = makeUpService;
        this.memberService = memberService;
    }

    // 사용자가 메뉴바에서 제품 추천 메뉴를 클릭시 이동
    @GetMapping("/recommendation")
    public String recommendation(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 제품을 출력하기 위해서 제품 정보를 데이터베이스에서 전부 호출
        List<Product> products = productService.findByAllProduct();

        // 제품 정보는 연관된 테이블이 많아 한 번에 전송하면 전송 중 오류가 발생하므로 나눠서 전송하기 위해 전송하려는 데이터에 대한 리스트 생성
        List<String> images = new ArrayList<>();
        List<String> productname = new ArrayList<>();
        List<Integer> price = new ArrayList<>();
        List<String> productlink = new ArrayList<>();
        String colorcode = new String();

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
        
        // 로그인된 사용자 정보 처리
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            // 로그인된 상태
            String currentUsername = authentication.getName(); // 사용자 아이디
            Member member = memberService.findById(currentUsername);  // Optional 사용
        
            List<MakeUp> makeups = makeUpService.findByMember(member);
            for (MakeUp makeUp : makeups) {
                colorcode = makeUp.getColor_code();
                break;  // 첫 번째 색상 코드만 가져옵니다.
            }
            model.addAttribute("colorcode", colorcode);  // colorcode를 모델에 추가
        }
        
        // 모델 객체를 생성해서 recommendation에 전송
        model.addAttribute("images", images);
        model.addAttribute("productname", productname);
        model.addAttribute("price", price);
        model.addAttribute("productlink", productlink);
        
        return "recommendation";
    }
}
