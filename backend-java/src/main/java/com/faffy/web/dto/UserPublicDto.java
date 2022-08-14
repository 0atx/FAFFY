package com.faffy.web.dto;

import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.type.Gender;
import com.faffy.web.jpa.type.LoginType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 비밀번호가 없는 공개 정보만 담은 Wrapper 클래스
 */
@Data
public class UserPublicDto {
    private int no;
    private String email;
    private String name;
    private String nickname;
    private LocalDate birthday;
    private Gender gender;

    private List<String> roles = new ArrayList<>();

    private List<String> categories = new ArrayList<>();
    /**
     * 자기소개 문구
     */
    private String info;
    private String introduce;

    private String loginType;
    private String instaLink;
    private String facebookLink;
    private String youtubeLink;

    @Builder
    public UserPublicDto(int no, String email, String name, String nickname, LocalDate birthday, Gender gender, String info, List<String> roles,
                         String introduce, String instaLink, String facebookLink, String youtubeLink, List<UserCategory> categories, LoginType loginType) {
        List<String> Categories = new ArrayList<>();
        for(UserCategory uc : categories){
            Categories.add(uc.getUserCategoryMapper().getCategory().getName());
        }

        this.no = no;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.info = info;
        this.roles = roles;
        this.introduce = introduce;
        this.instaLink = instaLink;
        this.facebookLink = facebookLink;
        this.youtubeLink = youtubeLink;
        this.categories = Categories;
        this.loginType = loginType.toString();
    }
}
