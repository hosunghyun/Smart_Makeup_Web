package com.smwhc.smart_makeup_web.Board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.smwhc.smart_makeup_web.Member.Member;

@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    // 1. 게시판 작성
    public Board saveBoard(Member member, String title, String content) {      // 사용자명, 제목, 내용 매개 변수로 전달 받음
        // 레포지토리에 저장하기 위한 Board 객체 생성
        Board board = new Board();  

        board.setMember(member);                // 객체에 Member 담기
        board.setTitle(title);                  // 객체에 제목 담기
        board.setContent_text(content);         // 객체에 내용 담기
        return boardRepository.save(board);     // 객체를 레포지토리에 저장하면서 저장된 게시판을 반환
    }

    // 2. 게시판 수정

    // 3. 게시판 삭제
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).get();
        boardRepository.delete(board);
    }

    // 4. 게시판 보기
    public List<Board> getBoardByPage(Integer pageNum,Integer pageSize) {
        // 가져올 페이지 정보를 담을 객체 생성
        Page<Board> board = boardRepository.findAll(PageRequest.of(pageNum, pageSize));

        // 게시판이 비었으면 null 반환
        if (board.isEmpty()) {
            return null;
        }

        // 게시판 전체 반환
        return board.getContent();
    }

    // 5. 게시판에 글 자세히 보기
    public Board getBoardByDetailPage(String board_id) {
        Long id = Long.parseLong(board_id);                     // 찾기 위해서는 입력받은 id를 Long 형으로 변환
        Optional<Board> board = boardRepository.findById(id);   // 레포지토리에서 id에 맞는 Board 객체 찾기
        return board.get();
    }
    public Board getBoardByDetailPage(Long board_id) {
        Optional<Board> board = boardRepository.findById(board_id);   // 레포지토리에서 id에 맞는 Board 객체 찾기
        return board.get();
    }
}
