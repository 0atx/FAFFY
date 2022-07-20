package com.faffy.web.dto;

import com.faffy.web.jpa.entity.UploadFile;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.type.Gender;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private int no;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private LocalDate birthday;
    private Gender gender;
    /**
     * 자기소개 문구
     */
    private String info;

    public UserDto(){};

    @Builder
    public UserDto(int no, String email, String name, String nickname, String password, LocalDate birthday, Gender gender, String info) {
        this.no = no;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.info = info;
    }

    public User toEntity() {
        User user = User.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(password)
                .birthday(birthday)
                .gender(gender)
                .info(info)
                .build();

        return user;
    }

    /**
     * Dto의 no를 제외한 정보들이 유효한지 검사
     * @return
     */
    public boolean isValid() {
        return true; // TODO 임시리턴값
    }
}
