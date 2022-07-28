package com.faffy.web.service;

import com.faffy.web.dto.CommentAddDto;
import com.faffy.web.dto.CommentUpdateDto;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.faffy.web.exception.ExceptionMsg.COMMENT_NOT_FOUND_MSG;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardService boardService;
    @Autowired
    UserService userService;

    @Override
    public List<Comment> getBoardComments(int boardNo) {
        List<Comment> comments = new ArrayList<>();
        try {
            Board board = boardService.getBoard(boardNo);
            return commentRepository.findByBoard(board);
        } catch (Exception e) {
            return comments;
        }
    }

    @Override
    @Transactional
    public void addComment(CommentAddDto commentDto) throws Exception {
        Board board = boardService.getBoard(commentDto.getBoard_no());
        User user = userService.getUserByNo(commentDto.getWriter_no());
        Comment comment = commentDto.toEntity(user,board);
        commentRepository.save(comment);

    }

    @Override
    @Transactional
    public void updateComment(CommentUpdateDto commentUpdateDto) throws Exception {
        Comment comment = commentRepository.findById(commentUpdateDto.getComment_no()).orElseThrow(()->new IllegalArgumentException(COMMENT_NOT_FOUND_MSG));
        comment.updateComment(commentUpdateDto);
    }

    @Override
    @Transactional
    public void deleteComment(int comment_no) throws Exception {
        commentRepository.deleteById(comment_no);
    }


}
