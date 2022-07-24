package com.faffy.web.jpa.type;

import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Embeddable
@Getter
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
