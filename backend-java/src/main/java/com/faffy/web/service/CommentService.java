package com.faffy.web.service;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.CommentAddDto;
import com.faffy.web.dto.CommentDto;
import com.faffy.web.dto.CommentUpdateDto;
import com.faffy.web.jpa.entity.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 게시글의 댓글 목록 요청
     * @param boardNo 게시글 번호
     * @return 해당 게시글의 댓글 목록
     */
    public List<Comment> getBoardComments(int boardNo);

    /**
     * 댓글 작성
     * @param commentAddDto 댓글 작성 관련 정보를 담은 객체
     * @throws Exception 입력이 잘못된 경우, 유저나 게시글 정보를 찾을 수 없을 경우 예외 발생
     */
    public void addComment(CommentAddDto commentAddDto) throws Exception;

    /**
     * 댓글 수정
     * @param commentUpdateDto 댓글 수정 정보를 담은 객체
     * @throws Exception 해당 댓글이 없거나 유효하지 않은 값 입력시 예외 발생
     */
    public void updateComment(CommentUpdateDto commentUpdateDto) throws Exception;

    /**
     * 댓글 삭제
     * @param comment_no 삭제하려는 댓글 번호
     * @throws Exception 해당 댓글이 없을경우 예외 발생
     */
    public void deleteComment(int comment_no) throws Exception;
}
