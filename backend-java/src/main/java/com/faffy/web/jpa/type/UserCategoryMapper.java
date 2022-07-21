package com.faffy.web.jpa.type;

import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.entity.User;
import lombok.Builder;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Embeddable
public class UserCategoryMapper implements Serializable {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_no", nullable = false)
    private FashionCategory category;

    public UserCategoryMapper(){}
    @Builder
    public UserCategoryMapper(User user, FashionCategory category) {
        this.user = user;
        this.category = category;
    }
}
