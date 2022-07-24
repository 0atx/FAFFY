package com.faffy.web.service;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.BoardUpdateDto;
import com.faffy.web.dto.UserDto;
import com.faffy.web.exception.ExceptionMsg;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.faffy.web.exception.ExceptionMsg.BOARD_NOT_FOUND_MSG;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserService userService;


    @Override
    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }

    @Override
    @Transactional
    public void writeBoard(int user_no, BoardDto boardDto) throws Exception {
        User u = userService.getUserByNo(user_no);
        boardRepository.save(boardDto.toEntityWriteBy(u));

    }

    @Override
    @Transactional
    public void updateBoard(BoardUpdateDto boardDto) throws Exception {
            Board board = getBoard(boardDto.getNo());
            board.updateBoard(boardDto);
    }

    @Override
    @Transactional
    public void deleteBoard(int no) throws Exception {
        try {
            boardRepository.deleteById(no);
        } catch (Exception e) {
            throw new Exception("게시글 삭제 실패! :" + e.getMessage());
        }
    }

    @Override
    public Board getBoard(int boardNo) {
        return boardRepository.findById(boardNo).orElseThrow(()->new IllegalArgumentException(BOARD_NOT_FOUND_MSG));
    }

}
