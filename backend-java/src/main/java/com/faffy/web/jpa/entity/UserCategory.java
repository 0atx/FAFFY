package com.faffy.web.jpa.entity;

import com.faffy.web.jpa.type.UserCategoryMapper;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : user_category entity
*/
@Entity(name = "User_Category")
@Getter
public class UserCategory {
    @EmbeddedId
    UserCategoryMapper userCategoryMapper;

    public UserCategory() {}
    @Builder
    public UserCategory(User user, FashionCategory category) {
        this.userCategoryMapper = UserCategoryMapper.builder().user(user).category(category).build();
    }
}
