package com.faffy.web.dto;

import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CommentGetDto {
    private String content;
    private UserPublicDto writer;
    private LocalDateTime datetime;

    public CommentGetDto() {}
    @Builder
    public CommentGetDto(String content, UserPublicDto writer, LocalDateTime datetime) {
        this.content = content;
        this.writer = writer;
        this.datetime = datetime;
    }

}
