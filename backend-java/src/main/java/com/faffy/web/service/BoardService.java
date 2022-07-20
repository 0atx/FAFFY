package com.faffy.web.service;


import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.UserDto;
import com.faffy.web.jpa.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    // 게시글 목록
    public List<Board> getAllBoard();
    // 게시글 등록
    public boolean writeBoard(UserDto userDto, BoardDto boardDto);

    // 게시글 수정
    // 게시글 삭제
    // 게시글 보기
    public Optional<Board> getBoard(int boardNo);

}