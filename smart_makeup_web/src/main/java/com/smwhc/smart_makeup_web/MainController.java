package com.smwhc.smart_makeup_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.smwhc.smart_makeup_web.Board.BoardService;
import com.smwhc.smart_makeup_web.Comment.CommentService;
import com.smwhc.smart_makeup_web.Image.ImageService;
import com.smwhc.smart_makeup_web.Makeup.MakeUpService;
import com.smwhc.smart_makeup_web.Product.ProductService;
import com.smwhc.smart_makeup_web.Product_Type.ProductTypeService;
import com.smwhc.smart_makeup_web.User.User;
import com.smwhc.smart_makeup_web.User.UserDTO;
import com.smwhc.smart_makeup_web.User.UserService;

@Controller
public class MainController {
    private final UserService userService;  // 회원 프레젠테이션 계층과 연결
    private final MakeUpService makeUpService;  // 화장 미리보기 프레젠테이션 계층과 연결
    private final ProductService productService;    // 화장품 프레젠테이션 계층과 연결
    private final ProductTypeService productTypeService;    // 화장품 종류 프레젠테이션 계층과 연결
    private final ImageService imageService;    // 이미지 프레젠테이션 계층과 연결
    private final BoardService boardService;    // 게시판 프레젠테이션 계층과 연결
    private final CommentService commentService;    // 댓글 프레젠테이션 계층과 연결

    // 생성자
    @Autowired
    public MainController(UserService userService, MakeUpService makeUpService, ProductService productService, 
                            ProductTypeService productTypeService, ImageService imageService,
                            BoardService boardService, CommentService commentService) {
        this.userService = userService;
        this.makeUpService = makeUpService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.imageService = imageService;
        this.boardService = boardService;
        this.commentService = commentService;
    }


    // 메인 페이지
    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "index";
    }

    @GetMapping("/makeup")
    public String makeup() {
        return "makeup";
    }

    @GetMapping("/board")
    public String board() {
        return "board";
    }
    
    // 로그인 페이지로 이동
    @GetMapping("/sign")
    public String signin() {
        return "sign";
    }

    // 로그인 페이지에서 입력된 아이디와 패스워드로 로그인
    @PostMapping("/login")
    public String login(@RequestParam String userid) {
        System.out.println(userid);
        return "redirect:/";
    }

    // 로그인 화면에서 회원가입 버튼을 클릭했을 때 회원 가입 페이지로 이동
    @GetMapping("/join")
    public String join() {
        return "join";
    }

    // 회원 가입 페이지에서 아이디, 비밀번호, 이메일, 전화번호를 입력했고 POST 형식으로 전송한 데이터를 받는 곳
    @PostMapping("/join_in")
    public String join_in(@RequestParam("join_id") String join_id, @RequestParam("join_password") String join_password, 
                        @RequestParam("join_email") String join_email, @RequestParam("join_phone") String join_phone) {
        UserDTO user = new UserDTO(join_id, join_password, join_email, join_phone);
        userService.save(user);

        return "index";
    }

    // 회원 가입시 중복확인을 위한 데이터 확인
    @PostMapping("/join/{id}")
    public ResponseEntity<String> getUserById(@RequestBody UserDTO users) {
        String result;
        if(userService.finduser(users.getUser_id()) == null) {      
            result = "null";    // 유저를 찾는 동작에서 null이 나오면 가입된 유저가 없다.
        }
        else {
            result = "exist";   // 유저가 있으므로 중복이다.
        }
        
        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }
}
