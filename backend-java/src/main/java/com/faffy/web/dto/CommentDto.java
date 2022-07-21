package com.faffy.web.dto;

import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CommentDto {
    private String content;
    private User writer;
    private Board board;
    private LocalDateTime datetime;

    public CommentDto() {}
    @Builder
    public CommentDto(String content, User writer, Board board, LocalDateTime datetime) {
        this.content = content;
        this.writer = writer;
        this.board = board;
        this.datetime = datetime;
    }

    public Comment toEntity() {
        Comment comment = Comment.builder()
                .board(board)
                .content(content)
                .writer(writer)
                .datetime(LocalDateTime.now())
                .build();

        return comment;
    }
}
