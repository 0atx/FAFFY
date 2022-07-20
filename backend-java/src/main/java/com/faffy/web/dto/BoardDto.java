package com.faffy.web.dto;

import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.BoardCategory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BoardDto {
    int no;
    private String title;
    private LocalDateTime datetime;
    private String content;
    private User user;
    private BoardCategory category;

    public BoardDto(){}

    @Builder
    public BoardDto(int no, String title, LocalDateTime datetime, String content, User user, BoardCategory category) {
        this.no = no;
        this.title = title;
        this.datetime = datetime;
        this.content = content;
        this.user = user;
        this.category = category;
    }

    public Board toEntityWriteBy(User user) {
        this.user = user;
        this.datetime = LocalDateTime.now();
        return toEntity();
    }
    public Board toEntity() {
        Board board = Board.builder()
                .no(no)
                .title(title)
                .datetime(datetime)
                .content(content)
                .user(user)
                .category(category)
                .build();

        return board;
    }
}
