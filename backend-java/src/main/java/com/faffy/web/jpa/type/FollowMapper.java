package com.faffy.web.jpa.type;

import com.faffy.web.jpa.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Embeddable
@Getter
public class FollowMapper implements Serializable {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follow_user_no", nullable = false)
   private User followUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "followed_user_no", nullable = false)
    private User followedUser;

    public FollowMapper(){}
    @Builder
    public FollowMapper(User from, User to) {
        this.followUser = from;
        this.followedUser = to;
    }

}
