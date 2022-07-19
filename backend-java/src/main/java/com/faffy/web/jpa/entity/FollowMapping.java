package com.faffy.web.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : follow_mapping table entity
*/
@Entity
@Data
@Table(name = "follow_mapping")
public class FollowMapping extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="follow_user_no")
    private User followUser;
    @ManyToOne(fetch = LAZY) @JoinColumn(name="followed_user_no")
    private User followedUser;

    public void setMapping(User from, User to) {
        followUser = from;
        followedUser = to;
    }
}
