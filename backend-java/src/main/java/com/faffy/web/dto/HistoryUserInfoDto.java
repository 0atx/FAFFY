package com.faffy.web.dto;

import com.faffy.web.jpa.entity.UserCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class HistoryUserInfoDto {
    private int no;
    private String nickname;
    private int followerCount;
    private int followingCount;
    private String introduce;
    private List<String> categories = new ArrayList<>();

    @Builder
    public HistoryUserInfoDto(int no, String nickname, int followerCount, int followingCount, String introduce, List<UserCategory> categories) {
        this.no = no;
        this.nickname = nickname;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.introduce = introduce;

        for (UserCategory category : categories) {
            this.categories.add(category.getUserCategoryMapper().getCategory().getName());
        }
    }
}
