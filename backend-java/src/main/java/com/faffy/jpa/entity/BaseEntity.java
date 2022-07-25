package com.faffy.jpa.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : base entity
최종 수정일 : 2022.07.18
*/
@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
}
