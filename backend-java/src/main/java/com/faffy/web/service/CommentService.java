package com.faffy.web.service;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.CommentDto;
import com.faffy.web.jpa.entity.Comment;

import java.util.List;

public interface CommentService {
    // 댓글 불러오기
    public List<Comment> getBoardComments(int boardNo);
    // 댓글 작성
    public void addComment(CommentDto commentDto) throws Exception;
    // 댓글 수정
    // 댓글 삭제
}
