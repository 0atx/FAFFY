package com.faffy.web.dto;

import com.faffy.web.exception.IllegalInputException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.util.StringUtils;

@Data
public class UserLoginDto {
    @NonNull
    private String email;
    @NonNull
    private String password;

    public UserLoginDto() {
    }

    /**
     * 이메일, 패스워드로 객체 생성
     * @param email
     * @param password
     * @throws IllegalInputException 입력값에 빈 칸이 있거나 null일경우 발생
     */
    @Builder
    public UserLoginDto(String email, String password) throws IllegalInputException {
        if (StringUtils.hasLength(email) && StringUtils.hasLength(password)) {
            setEmail(email);
            setPassword(password);
        } else {
            throw new IllegalInputException("입력값을 확인해 주세요.");
        }
    }
}
