package com.smwhc.smart_makeup_web;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smwhc.smart_makeup_web.Board.Board;
import com.smwhc.smart_makeup_web.Board.BoardDTO;
import com.smwhc.smart_makeup_web.Board.BoardService;
import com.smwhc.smart_makeup_web.Comment.CommentService;
import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Image.ImageService;
import com.smwhc.smart_makeup_web.Makeup.MakeUpService;
import com.smwhc.smart_makeup_web.Member.MemberDTO;
import com.smwhc.smart_makeup_web.Member.MemberService;
import com.smwhc.smart_makeup_web.Product.ProductService;
import com.smwhc.smart_makeup_web.Product_Type.ProductTypeService;

@Controller
public class MainController {
    private final MemberService memberService;  // 회원 프레젠테이션 계층과 연결
    private final MakeUpService makeUpService;  // 화장 미리보기 프레젠테이션 계층과 연결
    private final ProductService productService;    // 화장품 프레젠테이션 계층과 연결
    private final ProductTypeService productTypeService;    // 화장품 종류 프레젠테이션 계층과 연결
    private final ImageService imageService;    // 이미지 프레젠테이션 계층과 연결
    private final BoardService boardService;    // 게시판 프레젠테이션 계층과 연결
    private final CommentService commentService;    // 댓글 프레젠테이션 계층과 연결

    // 생성자
    @Autowired
    public MainController(MemberService memberService, MakeUpService makeUpService, ProductService productService, 
                            ProductTypeService productTypeService, ImageService imageService,
                            BoardService boardService, CommentService commentService) {
        this.memberService = memberService;
        this.makeUpService = makeUpService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.imageService = imageService;
        this.boardService = boardService;
        this.commentService = commentService;
    }
//------------------------------------------------------------------------------------------------------------------------------------------

    // 메인 페이지
    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "index";
    }

    @GetMapping("/makeup")
    public String makeup() {
        return "makeup";
    }
    
    // 로그인 페이지로 이동
    // 로그인 페이지에서 입력된 아이디와 패스워드로 로그인
    @GetMapping("/sign")
    public String signin() {
        return "sign";
    }

    // 로그인 화면에서 회원가입 버튼을 클릭했을 때 회원 가입 페이지로 이동
    @GetMapping("/join")
    public String join() {
        return "join";
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // 회원 가입 페이지에서 아이디, 비밀번호, 이메일, 전화번호를 입력했고 POST 형식으로 전송한 데이터를 받는 곳
    @PostMapping("/join_in")
    public String join_in(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "index";
    }

    // 회원 가입시 중복확인을 위한 데이터 확인
    @PostMapping("/join/{id}")
    public ResponseEntity<String> getUserById(@RequestBody MemberDTO memberDTO) {
        String result;
        if(memberService.findById(memberDTO.getMember_id()) == null) {      
            result = "null";    // 유저를 찾는 동작에서 null이 나오면 가입된 유저가 없다.
        }
        else {
            result = "exist";   // 유저가 있으므로 중복이다.
        }
        
        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 게시판 페이지
    @GetMapping("/board")
    public String board(Model model) {
        Integer pageSize = 10;  // 한 페이지에 보이는 글의 수

        // 게시글 가져오기
        List<Board> boards = boardService.getBoardByPage(0, pageSize);
        List<Image> images = new ArrayList<>();

        // 각 게시물에 대해 이미지 링크 추가
        for (Board board : boards) {
            String imageUrl = imageService.getImageUrlByBoardId(board.getBoard_id());
            // imageUrl이 "image_not_found"인 경우 대체 이미지로 설정
            if ("image_not_found".equals(imageUrl)) {
                imageUrl = "https://placeholder.com/50.jpg"; // 기본 이미지 URL로 대체
            }
            Image image = new Image();
            image.setBoard(board); // 게시물 ID 설정
            image.setImage_link(imageUrl); // 이미지 URL 설정
            images.add(image);
        }
        
        // 모델에 추가
        model.addAttribute("images", images);

        return "board";
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/boardwrite")
    public String boardwrite() {
        return "boardwrite";
    }
    // 게시판을 작성하는 부분
    @PostMapping("/write")
    public String write(@RequestParam("title") String title, 
                        @RequestParam("content") String content,
                        @RequestParam(value = "imageInput", required = false) MultipartFile file) {
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        // 게시판 작성
        Board board = new Board();
        board.setTitle(title);
        board.setContent_text(content); // 본문
        // 게시판을 데이터베이스에 저장하면서 게시판 PK 가져오기
        Long board_id = boardService.saveBoard(currentUsername, title, content).getBoard_id();

        // 이미지 업로드
        if(file != null && !file.isEmpty()) {
            try {
                // 절대 경로 지정
                String uploadDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\";
                File dir = new File(uploadDir);

                // 파일명 가져오고 경로와 합치기
                File uploadFile = new File(dir, file.getOriginalFilename());
                // 파일 저장
                file.transferTo(uploadFile);

                // 저장한 파일 경로 생성
                String imageLink = "/img/" + file.getOriginalFilename();

                // 데이터베이스에 저장
                imageService.saveBoardImageLink(board_id, imageLink);
            }
            catch(IOException e) {
                e.printStackTrace();;
            }
        }
        return "index";
    }
    // 게시판에 작성된 글 중 하나 보기
    @GetMapping("/boarddetail")
    public String boarddetail() {
        return "boarddetail";
    }

    @PostMapping("/opt")
    public String opt(@RequestParam("opts") String val) {
        System.out.println(val);
        return "makeup";
    }
}




//  <span th:text="${username}"> 을 사용하면 thymeleaf로 사용자 명 가져오기 가능