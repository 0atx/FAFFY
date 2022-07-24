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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private int no;
    @NonNull
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String nickname;
    @NonNull
    private String password;
    @NonNull
    private String birthday;
    @NonNull
    private Gender gender;
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
