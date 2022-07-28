package com.faffy.web.service;


import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.BoardUpdateDto;
import com.faffy.web.dto.UserDto;
import com.faffy.web.jpa.entity.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    /**
     * 모든 게시글 목록을 요청합니다.
     * @return 모든 게시글 리스트
     */
    public List<Board> getAllBoard();

    /**
     * 게시글 작성
     * @param boardDto 게시글 내용을 담은 객체
     * @throws Exception 글쓴이 번호에 해당하는 유저를 찾을 수 없거나 입력값이 잘못된 경우 예외 발생
     */
    public void writeBoard(BoardDto boardDto) throws Exception;

    /**
     * 게시글 수정
     * @param boardUpdateDto 수정할 게시글의 내용을 담은 객체
     * @throws Exception 해당 게시글이 없거나 유효하지 않은 입력 시 예외 발생
     */
    public void updateBoard(BoardUpdateDto boardUpdateDto) throws Exception;

    /**
     * 게시글 삭제
     * @param no 삭제하려는 게시글 번호
     * @throws Exception 해당 게시글이 없을경우 예외 발생
     */
    public void deleteBoard(int no) throws Exception;

    /**
     * 게시글 번호에 해당하는 게시글 상세보기
     * @param boardNo 보려는 게시글의 번호
     * @return 게시글 상세정보
     * @throws Exception 해당 게시글이 없을 경우 예외 발생
     */
    public Board getBoard(int boardNo) throws Exception;

}