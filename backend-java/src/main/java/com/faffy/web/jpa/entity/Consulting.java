package com.faffy.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Consulting extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "consultant_no")
    private User consultant;
    @Column(nullable = false)
    private String title;
    private int roomSize;
    private int viewCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_no")
    private FashionCategory category;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
}

