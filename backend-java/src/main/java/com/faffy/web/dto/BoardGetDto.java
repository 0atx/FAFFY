package com.faffy.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BoardGetDto {
    private int no;
    private String title;
    private String content;
    private String dateTime;
    private UserPublicDto user;

    public BoardGetDto() {
    }

    @Builder
    public BoardGetDto(int no, String title, String content, String dateTime, UserPublicDto user) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.user = user;
    }
}
