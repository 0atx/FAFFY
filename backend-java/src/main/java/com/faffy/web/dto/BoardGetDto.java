package com.faffy.web.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 게시글 정보 응답 시 답을 Wrapper 클래스
 */
@Data
public class BoardGetDto {
    private int no;
    private String title;
    private String content;
    private String dateTime;
    private UserPublicDto user;
    private int hit;
    private int commentCount;

    public BoardGetDto() {
    }

    @Builder
    public BoardGetDto(int no, String title, String content, String dateTime, UserPublicDto user,
                       int hit, int commentCount) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.user = user;
        this.hit=hit;
        this.commentCount = commentCount;
    }
}
