package com.faffy.web.dto;

import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.UploadFile;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.type.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private int no;
    @NonNull
    @Email(message="이메일 형식이 올바르지 않습니다.ex)***@***.***")
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String nickname;
    @NonNull
    @Pattern(regexp = "(^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,15}$)",message = "비밀번호는 영문 대문자, 소문자, 숫자, 특수문자를 1개이상 포함한 8자 ~ 15자여야 합니다.")
    private String password;
    @NonNull
    private String birthday;
    @NonNull
    private Gender gender;

    private List<String> roles = new ArrayList<>();
    /**
     * 자기소개 문구
     */
    private String info;

    public UserDto(){};

    @Builder
    public UserDto(int no, String email, String name, String nickname, String password, String birthday, Gender gender, String info) throws IllegalInputException{
        if (StringUtils.hasLength(email) && StringUtils.hasLength(name)
        && StringUtils.hasLength(password)) {
            this.no = no;
            this.email = email;
            this.name = name;
            this.nickname = nickname;
            this.password = password;
            this.birthday = birthday;
            this.gender = gender;
            this.info = info;
        } else {
            throw new IllegalInputException("빈 값이 존재합니다.");
        }
    }

    public User toEntity() throws IllegalInputException {
        if (!isValid())
            throw new IllegalInputException("필수 입력란을 확인해 주세요.");
        System.out.println("toENtity");
        User user = User.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(password)
                .birthday(LocalDate.parse(birthday))
                .gender(gender)
                .info(info)
                .roles(roles)
                .build();

        return user;
    }

    public boolean isValid() {
        if (StringUtils.hasLength(email) && StringUtils.hasLength(name)
                && StringUtils.hasLength(password) && StringUtils.hasLength(nickname)
                && StringUtils.hasLength(birthday) && gender != null) {
            return true;
        }
        return false;
    }



}