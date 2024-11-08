package com.smwhc.smart_makeup_web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.smwhc.smart_makeup_web.Board.BoardService;
import com.smwhc.smart_makeup_web.Comment.Comment;
import com.smwhc.smart_makeup_web.Comment.CommentDTO;
import com.smwhc.smart_makeup_web.Comment.CommentService;
import com.smwhc.smart_makeup_web.Member.MemberService;

@Controller
public class CommentController {
    @Autowired
    private final MemberService memberService;  // 회원 프레젠테이션 계층과 연결
    private final BoardService boardService;    // 게시판 프레젠테이션 계층과 연결
    private final CommentService commentService;    // 댓글 프레젠테이션 계층과 연결

    public CommentController(MemberService memberService, BoardService boardService, CommentService commentService) {
        this.memberService = memberService;
        this.boardService = boardService;
        this.commentService = commentService;
    }

    // 게시판에 댓글 작성하기
    @PostMapping("/write/content/{id}")
    public String writecontent(@RequestParam("contents") String content, @PathVariable("id") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        Comment comment = new Comment();    // 댓글을 저장하기 위한 객체

        // 인증 정보가 null이거나 익명 사용자라면 로그인 페이지로 리다이렉트
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getName().equals("anonymousUser")) {
            return "redirect:/sign"; // 로그인 페이지로 리다이렉트
        }

        comment.setBoard(boardService.getBoardByDetailPage(id));    // 객체에 게시판 아이디 담기
        comment.setComment_content(content);                        // 객체에 댓글 내용 담기 
        comment.setMember(memberService.findById(currentUsername)); // 객체에 작성자 담기

        commentService.saveComment(comment);    // 객체를 레포지토리에 전송해서 저장하기

        return "redirect:/boarddetail/id=" + id;
    }

    // 게시판에 작성된 댓글 삭제하는 기능
    @PostMapping("/deletecomment")
    public ResponseEntity<String> deletecomment(@RequestBody CommentDTO commentDTO){
        String result;

        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        // 삭제하려는 댓글을 데이터베이스에서 찾아서 객체로 생성
        Comment comment = commentService.findById(commentDTO.getComment_id());
        
        // 삭제하려는 댓글 작성자하고 현재 삭제하려는 사용자하고 같은지 비교
        if(currentUsername.equals(comment.getMember().getMember_id())) {        
            commentService.deletecomment(commentDTO.getComment_id());
            result = "success";
        }
        else {
            result = "fails";
        }

        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }

    // 게시판에 작성된 댓글 수정하는 기능
    
}
