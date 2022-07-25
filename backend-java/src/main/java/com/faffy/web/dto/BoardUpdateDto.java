package com.faffy.web.dto;

import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.BoardCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Data
public class BoardUpdateDto {
    @NonNull
    int no;
    @NonNull
    private int writer_no;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private BoardCategory category;

    public BoardUpdateDto() {
    }

    @Builder
    public BoardUpdateDto(@NonNull int no,@NonNull int writer_no, @NonNull String title, @NonNull String content, @NonNull BoardCategory category) throws IllegalInputException{
        if (StringUtils.hasLength(title) && StringUtils.hasLength(content)) {
            this.no = no;
            this.writer_no = writer_no;
            this.title = title;
            this.content = content;
            this.category = category;
        } else {
            throw new IllegalInputException("유효하지 않은 값이 존재합니다.");
        }
    }
}
