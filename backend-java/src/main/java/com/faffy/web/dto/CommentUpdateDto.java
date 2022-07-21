package com.faffy.web.dto;

import com.faffy.web.exception.IllegalInputException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.util.StringUtils;

@Data
public class CommentUpdateDto {
    @NonNull
    int comment_no;
    @NonNull
    String content;
    public CommentUpdateDto() {}

    @Builder
    public CommentUpdateDto(@NonNull int comment_no, @NonNull String content) throws IllegalInputException {
        if (!StringUtils.hasLength(content))
            throw new IllegalInputException("내용을 입력해 주세요.");

        this.comment_no = comment_no;
        this.content = content;
    }
}
