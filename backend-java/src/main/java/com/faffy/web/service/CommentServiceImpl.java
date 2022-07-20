package com.faffy.web.service;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.CommentDto;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardService boardService;

    @Override
    public List<Comment> getBoardComments(int boardNo) {
        Optional<Board> boardResult = boardService.getBoard(boardNo);
        List<Comment> comments = new ArrayList<>();
        if (boardResult.isEmpty())
            return comments;

        return commentRepository.findByBoard(boardResult.get());
    }

    @Override
    public void addComment(CommentDto commentDto) throws Exception {
        Comment comment = commentDto.toEntity();
        commentRepository.save(comment);

    }
}
