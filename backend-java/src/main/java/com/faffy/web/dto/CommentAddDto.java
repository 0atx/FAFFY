package com.faffy.web.dto;

import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Data
public class CommentAddDto {
    @NonNull
    private int board_no;
    @NonNull
    private int writer_no;
    @NonNull
    private String content;
    public CommentAddDto() {}

    @Builder
    public CommentAddDto(@NonNull int board_no, @NonNull int writer_no, @NonNull String content) throws IllegalInputException{
        if (!StringUtils.hasLength(content))
            throw new IllegalInputException("내용을 입력해 주세요.");

        this.board_no = board_no;
        this.writer_no = writer_no;
        this.content = content;
    }

    public Comment toEntity(User writer, Board board) {
        return Comment.builder()
                .board(board)
                .writer(writer)
                .content(getContent())
                .datetime(LocalDateTime.now())
                .build();

    }
}
