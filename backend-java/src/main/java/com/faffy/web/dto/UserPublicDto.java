package com.faffy.web.dto;

import com.faffy.web.jpa.type.Gender;
import com.faffy.web.jpa.type.LoginType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

/**
 * 비밀번호가 없는 공개 정보만 담은 Wrapper 클래스
 * 주로 응답할 때 사용
 */
@Data
public class UserPublicDto {
    private int no;
    private String email;
    private String name;
    private String nickname;
    private LocalDate birthday;
    private Gender gender;

    private List<String> roles;
    /**
     * 자기소개 문구
     */
    private String info;
    private String introduce;
    @NonNull
    private LoginType loginType;

    private String instaLink;
    private String facebookLink;
    private String youtubeLink;

    @Builder
    public UserPublicDto(int no, String email, String name, String nickname, LocalDate birthday, Gender gender, String info, List<String> roles,
                         String introduce, LoginType loginType, String instaLink,String facebookLink,String youtubeLink) {
        this.no = no;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.info = info;
        this.roles = roles;
        this.introduce = introduce;
        this.loginType = loginType;
        this.instaLink = instaLink;
        this.facebookLink = facebookLink;
        this.youtubeLink = youtubeLink;
    }
}
