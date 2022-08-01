package com.faffy.web.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Consulting extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "consultant_no")
    private User consultant;

    @Column(nullable = false)
    private String title;
    private int roomSize;
    private int viewCount;

    @Column(length = 100)
    private String intro;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "category_no")
//    private FashionCategory category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY, mappedBy = "consultingCategoryMapper.consulting")
    @JsonIgnore
    private List<ConsultingCategory> categories = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

