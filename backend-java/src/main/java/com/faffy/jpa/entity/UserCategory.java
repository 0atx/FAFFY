package com.faffy.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : user_category entity
*/
@Entity(name = "User_Category")
@Data
public class UserCategory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_no")
    private FashionCategory category;
}
