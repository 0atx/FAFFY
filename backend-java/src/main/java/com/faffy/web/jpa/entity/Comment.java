package com.faffy.web.jpa.entity;

import com.faffy.web.dto.CommentGetDto;
import com.faffy.web.dto.CommentUpdateDto;
import com.faffy.web.exception.IllegalInputException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Comment extends BaseEntity {
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_no")
    private User writer;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_no")
    @JsonIgnore
    private Board board;
    private LocalDateTime datetime;
    public Comment() {}

    @Builder
    public Comment(String content, User writer, Board board, LocalDateTime datetime) {
        this.content = content;
        this.writer = writer;
        this.board = board;
        this.datetime = datetime;
    }

    public void updateComment(CommentUpdateDto commentUpdateDto) {
        this.content = commentUpdateDto.getContent();
        this.datetime = LocalDateTime.now();
    }

    public void addTo(Board board) {
        this.board = board;
    }

    public CommentGetDto toCommentGetDto() {
        CommentGetDto commentGetDto = CommentGetDto.builder()
                .no(getNo())
                .content(getContent())
                .writer(getWriter().toSimpleDto())
                .datetime(getDatetime())
                .build();
        return commentGetDto;
    }
}
