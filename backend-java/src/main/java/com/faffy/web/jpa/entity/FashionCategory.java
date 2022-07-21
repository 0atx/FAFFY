package com.faffy.web.jpa.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : fashion_category entity
최종 수정일 : 2022.07.18
*/
@Entity
@Getter
@Table(name = "fashion_category")
public class FashionCategory extends BaseEntity {
    @Column(nullable = false)
    private String name;
    public FashionCategory() {}
    @Builder
    public FashionCategory(String name) {
        this.name = name;
    }
}
