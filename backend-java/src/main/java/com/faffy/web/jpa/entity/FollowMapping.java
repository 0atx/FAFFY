package com.faffy.web.jpa.entity;

import com.faffy.web.jpa.type.FollowMapper;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : follow_mapping table entity
*/
@Entity
@Getter
@Table(name = "follow_mapping")
public class FollowMapping  {
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name="follow_user_no")
//    private User followUser;
//    @ManyToOne(fetch = LAZY) @JoinColumn(name="followed_user_no")
//    private User followedUser;
    @EmbeddedId
    private FollowMapper followMapper;

    public void setMapping(User from, User to) {
        followMapper = FollowMapper.builder()
                .from(from)
                .to(to)
                .build();
    }
}
