package com.faffy.web.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class UserGetSimpleDto {
    private int no;
    private String nickname;

    @Builder
    public UserGetSimpleDto(int no,String nickname) {
        setNo(no);
        setNickname(nickname);
    }
}
