package com.faffy.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name = "category_no")
    private FashionCategory category;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
}

