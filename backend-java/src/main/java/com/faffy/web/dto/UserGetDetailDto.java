package com.faffy.web.dto;

import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.type.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserGetDetailDto {
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
    private List<String> categories = new ArrayList<>();

    private String instaLink;
    private String facebookLink;
    private String youtubeLink;

    @Builder
    public UserGetDetailDto(int no, String email, String name, String nickname, LocalDate birthday, Gender gender, String info, List<String> roles,
                         String introduce, List<UserCategory> categories, String instaLink, String facebookLink, String youtubeLink) {
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

        for (UserCategory category:
                categories) {
            this.categories.add(category.getUserCategoryMapper().getCategory().getName());
        }
    }
}
