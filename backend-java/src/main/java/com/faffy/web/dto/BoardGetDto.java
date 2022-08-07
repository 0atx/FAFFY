package com.faffy.web.dto;

import com.faffy.web.jpa.entity.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 게시글 정보 응답 시 답을 Wrapper 클래스
 */
@Data
public class BoardGetDto {
    private int no;
    private String title;
    private String content;
    private String category;
    private String dateTime;
    private UserGetSimpleDto user;
    private int hit;
    private int commentCount;

    private int fileNo;

    public BoardGetDto() {
    }

    @Builder
    public BoardGetDto(int no, String title, String content, String dateTime, UserGetSimpleDto user,
                       int hit, int commentCount, String category) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.category = category;
        this.dateTime = dateTime;
        this.user = user;
        this.hit=hit;
        this.commentCount = commentCount;
    }
}
