package com.faffy.web.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : user_category entity
*/
@Entity(name = "User_Category")
@Data
public class UserCategory extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_no")
    private FashionCategory category;
}
