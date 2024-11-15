package com.smwhc.smart_makeup_web.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smwhc.smart_makeup_web.Board.Board;
import com.smwhc.smart_makeup_web.Board.BoardDTO;
import com.smwhc.smart_makeup_web.Board.BoardService;
import com.smwhc.smart_makeup_web.Comment.Comment;
import com.smwhc.smart_makeup_web.Comment.CommentService;
import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Image.ImageService;
import com.smwhc.smart_makeup_web.Member.Member;
import com.smwhc.smart_makeup_web.Member.MemberService;

@Controller
public class BoardController {
    @Autowired
    private final MemberService memberService;  // 회원 프레젠테이션 계층과 연결
    private final BoardService boardService;    // 게시판 프레젠테이션 계층과 연결
    private final ImageService imageService;    // 이미지 프레젠테이션 계층과 연결
    private final CommentService commentService;    // 댓글 프레젠테이션 계층과 연결

    public BoardController(MemberService memberService, BoardService boardService, ImageService imageService, CommentService commentService) {
        this.memberService = memberService;
        this.boardService = boardService;
        this.imageService = imageService;
        this.commentService = commentService;
    }

    // 게시판에 글 작성하는 부분으로 이동
    @GetMapping("/boardwrite")
    public String boardwrite() {
        return "boardwrite";
    }
    
    // 게시판 페이지
    @GetMapping("/board")
    public String board(@RequestParam(value="page", required = false) String page, Model model) {
        Integer pageSize = 6;  // 한 페이지에 보이는 글의 수
        if (page == null) {
            return "redirect:/board?page=1";
        }
        Integer pageNum = Integer.parseInt(page);

        // 게시글 가져오기
        List<Board> boards = boardService.getBoardByPage(pageNum - 1, pageSize);  // 게시판 가져와서 보여주기 0페이지로 설정되었으므로 나중에 수정할 부분

        Integer size = boardService.getBoardByPageSize();
        if (boards == null) {
            boards = new ArrayList<>(); // boards가 null인 경우 빈 리스트로 초기화
        }
        
        // 모델에 추가
        model.addAttribute("boards", boards);
        model.addAttribute("size", size);
        return "board";
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
                    String uploadDir = System.getProperty("user.dir") + "\\smart_makeup_web\\src\\main\\resources\\static\\img\\";
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
        
        return "redirect:/board";
    }

    // 게시판에 작성된 글 중 하나 보기
    @GetMapping("/boarddetail/id={id}")
    public String boarddetail(@PathVariable("id") String id, Model model) {     // 어느 것을 확대해서 볼지 게시판 아이디를 전달 받기
        // 1. 입력받은 board 테이블의 아이디로 Board 찾기
        Board board = boardService.getBoardByDetailPage(id);

        // 2. 글에 작성된 댓글 찾기
        List<Comment> contents = new ArrayList<>();
        contents = commentService.findByComments(id);

        // 6. 모델에 추가하기
        model.addAttribute("contents", contents);
        model.addAttribute("board", board);

        return "boarddetail";
    }

    // 게시판 글 수정하기위해 사용자가 맞는지 확인
    @GetMapping("/checkuser/board_id={id}")
    public ResponseEntity<String> boardedit(@PathVariable("id") String id) {
        String result;

        // 1. 수정하기 위해서는 작성자가 맞는지 확인 절차가 필요 그래서 현재 로그인 중인 사용자 정보 받기
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        // 2. 게시판을 작성한 사용자 아이디 가져오기
        Board board = boardService.getBoardByDetailPage(id);    // 게시판 id를 받았기 때문에 해당 값으로 board 객체 찾기

        if(currentUsername.equals(board.getMember().getMember_id())) {
            result = "WRITTER";  // 사용자명이 같기 때문에 "WRITTER"로 설정
        }
        else {
            result = "NO-WRITTER";     // 사용자명이 다르기 때문에 "NO-WRITTER"로 설정
        }

        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }
    
    // 게시판 글 수정하기
    @GetMapping("/edit/board_id={id}")
    public String edit(@PathVariable("id") String id, Model model) {
        // 1. 수정하기 위해서는 기존 데이터가 필요하니 전송하기
        Board board = boardService.getBoardByDetailPage(id);
        model.addAttribute("board", board);
        
        model.addAttribute("boardid", id);
        return "edit";
    }

    // 게시판 수정 완료
    @PostMapping("/editboard")
    public String editboard(@RequestParam(value="id", required = false) String boardid,
                            @RequestParam("title") String title, 
                            @RequestParam("content") String content,
                            @RequestParam(value = "imageInput", required = false) List<MultipartFile> files,
                            @RequestParam("deleteimg") List<String> deleteArray) {
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        Board board = new Board();
        Member member = memberService.findById(currentUsername);

        // 이미지 삭제
        
        for(String img : deleteArray) {
            String result = img.replace("\"", "")   // 쌍따옴표 제거
                           .replace("[", "")  // 여는 대괄호 제거
                           .replace("]", ""); // 닫는 대괄호 제거
            if (result != null && !result.isEmpty()) {
                // 1. 디렉터리에 저장된 이미지 삭제하기
                Image image = imageService.findByImage(result);
                String Dir = System.getProperty("user.dir") + "\\smart_makeup_web\\src\\main\\resources\\static";
                File file = new File(Dir + image.getImage_link());
                file.delete();

                imageService.deleteImage(image);
            }
        }

        board.setId(Long.parseLong(boardid));
        board.setTitle(title);
        board.setContent_text(content);
        board.setMember(member);
        
        boardService.updateBoard(board);
        
        // 이미지를 저장하기 위한 부분
        for(MultipartFile file : files) {   // 이미지가 여러개 일 수 있으므로 for-Each반복문 사용
            if (file != null && !file.isEmpty()) {  // 이미지가 있는 경우에만 실행
                try {
                    // 절대 경로 지정
                    String uploadDir = System.getProperty("user.dir") + "\\smart_makeup_web\\src\\main\\resources\\static\\img\\";
                    File dir = new File(uploadDir);
                    
                    // 파일명 생성 (현재 시간 + 원래 파일명)
                    String newFileName = currentUsername + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File uploadFile = new File(dir, newFileName);

                    // 파일 저장
                    file.transferTo(uploadFile);

                    // 저장한 파일 경로 생성
                    String imageLink = "/img/" + newFileName;

                    // 데이터베이스에 저장
                    
                    imageService.saveBoardImageLink(boardService.getBoardByDetailPage(boardid), imageLink);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "redirect:/board";
    }

    // 게시판 글 삭제하는 기능
    @PostMapping("/delete/board={id}")
    public ResponseEntity<String> deleteboard(@RequestBody BoardDTO boardDTO) {
        String result;

        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        Board board = boardService.getBoardByDetailPage(boardDTO.getBoard_id());
        String Dir = System.getProperty("user.dir") + "\\smart_makeup_web\\src\\main\\resources\\static";

        if(currentUsername.equals(board.getMember().getMember_id())) {
            for(Image image : board.getImages()) {  // 게시판에 이미지 링크를 전부 가져와서 해당 경로의 이미지를 전부 삭제
                File file = new File(Dir + image.getImage_link());
                file.delete();
            }
            boardService.deleteBoard(board.getId());    // 게시판을 지우면 이미지 데이터베이스 또한 함께 삭제 된다.
            result = "successs";
        }
        else {
            result = "fails";
        }

        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }
}
