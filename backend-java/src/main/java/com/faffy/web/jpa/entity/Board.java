package com.faffy.web.jpa.entity;

import com.faffy.web.jpa.type.BoardCategory;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Board extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Lob
    @Column(length=2000)
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_no")
    private User user;
    @Enumerated(EnumType.STRING)
    private BoardCategory category;
}
