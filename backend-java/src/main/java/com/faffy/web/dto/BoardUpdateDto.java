package com.faffy.web.dto;

import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.BoardCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 수정 요청 시 request로 받는 객체
 */
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
    private MultipartFile file;

    public BoardUpdateDto() {
    }

    @Builder
    public BoardUpdateDto(@NonNull int no,@NonNull int writer_no, @NonNull String title, @NonNull String content,
                          @NonNull BoardCategory category, MultipartFile file) throws IllegalInputException{
        if (StringUtils.hasLength(title) && StringUtils.hasLength(content)) {
            this.no = no;
            this.writer_no = writer_no;
            this.title = title;
            this.content = content;
            this.category = category;
            this.file = file;
        } else {
            throw new IllegalInputException("유효하지 않은 값이 존재합니다.");
        }
    }


}
