package com.faffy.web.dto;

import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.type.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 비밀번호가 없는 공개 정보만 담은 객체
 */
@Data
public class UserPublicDto {
    private int no;
    private String email;
    private String name;
    private String nickname;
    private LocalDate birthday;
    private Gender gender;
    /**
     * 자기소개 문구
     */
    private String info;
    @Builder
    public UserPublicDto(int no, String email, String name, String nickname, LocalDate birthday, Gender gender, String info) {
        this.no = no;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.info = info;
    }
}
