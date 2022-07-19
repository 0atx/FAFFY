package com.faffy.web.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : fashion_category entity
최종 수정일 : 2022.07.18
*/
@Entity
@Data
@Table(name = "fashion_category")
public class FashionCategory extends BaseEntity {
    private String name;

}
