package com.smwhc.smart_makeup_web.Board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.smwhc.smart_makeup_web.Member.Member;
import com.smwhc.smart_makeup_web.Member.MemberRepository;

@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private final MemberRepository memberRepository;

    public BoardService(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }
    
    // 1. 게시판 작성
    public Board saveBoard(String currentUsername, String title, String content) {
        Board board = new Board();
        Optional<Member> member = memberRepository.findById(currentUsername);
        board.setMember(member.get());
        board.setTitle(title);
        board.setContent_text(content);
        return boardRepository.save(board);
    }

    // 2. 게시판 수정

    // 3. 게시판 삭제

    // 4. 게시판 보기
    public List<Board> getBoardByPage(Integer pageNum,Integer pageSize) {
        // 가져올 페이지 정보를 담을 객체 생성
        Page<Board> board = boardRepository.findAll(PageRequest.of(pageNum, pageSize));

        if (board.isEmpty()) {
            return null;
        }
        return board.getContent();
    }
}
