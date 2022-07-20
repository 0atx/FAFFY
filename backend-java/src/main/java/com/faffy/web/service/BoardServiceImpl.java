package com.faffy.web.service;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.UserDto;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public boolean writeBoard(UserDto userDto, BoardDto boardDto) {
        Optional<User> findUser = userService.getUserByNo(userDto.getNo());
        if (findUser.isEmpty())
            return false;


        try {
            boardRepository.save(boardDto.toEntityWriteBy(findUser.get()));
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public Optional<Board> getBoard(int boardNo) {
        return boardRepository.findById(boardNo);
    }

}
