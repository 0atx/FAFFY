package com.faffy.web.dto;

import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CommentGetDto {
    private int no;
    private String content;
    private UserGetSimpleDto writer;
    private LocalDateTime datetime;

    public CommentGetDto() {}

    @Builder
    public CommentGetDto(int no, String content, UserGetSimpleDto writer, LocalDateTime datetime) {
        this.no = no;
        this.content = content;
        this.writer = writer;
        this.datetime = datetime;
    }

}
