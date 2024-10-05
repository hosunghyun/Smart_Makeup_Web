package com.smwhc.smart_makeup_web;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smwhc.smart_makeup_web.Board.Board;
import com.smwhc.smart_makeup_web.Board.BoardService;
import com.smwhc.smart_makeup_web.Comment.Comment;
import com.smwhc.smart_makeup_web.Comment.CommentService;
import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Image.ImageService;
import com.smwhc.smart_makeup_web.Makeup.MakeUpService;
import com.smwhc.smart_makeup_web.Member.Member;
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
        List<Board> boards = boardService.getBoardByPage(0, pageSize);  // 게시판 가져와서 보여주기 0페이지로 설정되었으므로 나중에 수정할 부분

        if (boards == null) {
            boards = new ArrayList<>(); // boards가 null인 경우 빈 리스트로 초기화
        }

        List<Image> images = new ArrayList<>();     // 이미지 저장하기 위한 리스트

        // 각 게시물에 대해 이미지 링크 추가
        for (Board board : boards) {    // 게시판에 글은 하나가 아니므로 반복문으로 이미지 가져오기
            String imageUrl;    // 이미지 저장된 링크

            if(imageService.getImageUrlByBoardId(board.getId()).isEmpty()) {    // 이미지는 NULL을 허용했으므로 작성된 글에 이미지가 없을 수 있다.
                imageUrl = "image_not_found";
            }
            else {
                // 글에 이미지가 있기 때문에 이미지 링크를 가져오는데 이미지 링크는 여러개 일 수 있으므로 첫번째 이미지만 가져와서 썸네일로 사용
                imageUrl = imageService.getImageUrlByBoardId(board.getId()).get(0).getImage_link(); 
            }
            // imageUrl이 "image_not_found"인 경우 대체 이미지로 설정
            if ("image_not_found".equals(imageUrl)) {
                imageUrl = "https://placeholder.com/50.jpg"; // 기본 이미지 URL로 대체
            }

            Image image = new Image();  // 이미지 객체 생성

            image.setBoard(board); // 게시물 ID 설정
            image.setImage_link(imageUrl); // 이미지 URL 설정
            images.add(image);
            
        }
        
        // 모델에 추가
        model.addAttribute("images", images);

        return "board";
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // 게시판에 글 작성하는 부분으로 이동
    @GetMapping("/boardwrite")
    public String boardwrite() {
        return "boardwrite";
    }

    // 게시판을 작성하는 부분
    @PostMapping("/write")
    public String write(@RequestParam("title") String title, 
                        @RequestParam("content") String content,
                        @RequestParam(value = "imageInput", required = false) List<MultipartFile> files) {  // 제목, 내용, 이미지들을 파라미터로 받는다.
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        // 게시판 작성을 위한 객체 생성
        Board board = new Board();
        Member member = memberService.findById(currentUsername);

        // 객체에 제목과 내용을 담기
        board.setTitle(title);
        board.setContent_text(content);

        // 게시판을 데이터베이스에 저장하면서 게시판 PK 가져오기
        Long board_id = boardService.saveBoard(member, title, content).getId();

        // 이미지를 저장하기 위한 부분
        for(MultipartFile file : files) {   // 이미지가 여러개 일 수 있으므로 for-Each반복문 사용
            if (file != null && !file.isEmpty()) {  // 이미지가 있는 경우에만 실행
                try {
                    // 절대 경로 지정
                    String uploadDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\";
                    File dir = new File(uploadDir);
                    
                    
                    // 파일명 생성 (현재 시간 + 원래 파일명)
                    String newFileName = currentUsername + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File uploadFile = new File(dir, newFileName);

                    // 파일 저장
                    file.transferTo(uploadFile);

                    // 저장한 파일 경로 생성
                    String imageLink = "/img/" + newFileName;

                    // 데이터베이스에 저장
                    
                    imageService.saveBoardImageLink(boardService.getBoardByDetailPage(String.valueOf(board_id)), imageLink);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return "index";
    }

    // 게시판에 작성된 글 중 하나 보기
    @GetMapping("/boarddetail/id={id}")
    public String boarddetail(@PathVariable("id") String id, Model model) {     // 어느 것을 확대해서 볼지 게시판 아이디를 전달 받기
        String imageUrl;    // 이미지 링크인데 이미지가 없을 경우
        List<Image> imageUrls;  // 이미지 링크들
        Image image = new Image();

        // 1. 입력받은 board 테이블의 아이디로 Board 찾기
        Board board = boardService.getBoardByDetailPage(id);

        // 2. Board 테이블의 아이디로 사용된 이미지 찾기
        List<Image> images = new ArrayList<>();

        // 3. 이미지가 없다면 url은 대체 이미지로 하기
        if(imageService.getImageUrlByBoardId(board.getId()).isEmpty()) {
            imageUrl = "https://placeholder.com/50.jpg"; // 기본 이미지 URL로 대체
            image.setBoard(board); // 게시물 ID 설정
            image.setImage_link(imageUrl); // 이미지 URL 설정
            model.addAttribute("image", image); // 모델 생성
            return "boarddetail";
        }

        // 4. 이미지가 있다면 아이디로 찾아오기
        imageUrls = imageService.getImageUrlByBoardId(board.getId());

        for(Image img : imageUrls) {    // 가져온 이미지들을 반복해서 모델에 넣기
            image.setBoard(board); // 게시물 ID 설정
            image.setImage_link(img.getImage_link()); // 이미지 URL 설정
            images.add(image);  // 리스트에 객체 저장하기
        }

        // 5. 글에 작성된 댓글 찾기
        List<Comment> contents = new ArrayList<>();
        
        contents = commentService.findByComments(id);

        // 6. 모델에 추가하기
        model.addAttribute("image", images);
        model.addAttribute("contents", contents);

        return "boarddetail";
    }

    // 게시판에 댓글 작성하기
    @PostMapping("/write/content/{id}")
    public String writecontent(@RequestParam("contents") String content, @PathVariable("id") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        Comment comment = new Comment();    // 댓글을 저장하기 위한 객체

        comment.setBoard(boardService.getBoardByDetailPage(id));    // 객체에 게시판 아이디 담기
        comment.setComment_content(content);                        // 객체에 댓글 내용 담기 
        comment.setMember(memberService.findById(currentUsername)); // 객체에 작성자 담기

        commentService.saveComment(comment);    // 객체를 레포지토리에 전송해서 저장하기

        return "redirect:/index";
    }
}




//  <span th:text="${username}"> 을 사용하면 thymeleaf로 사용자 명 가져오기 가능