package com.faffy.web.jpa.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Comment extends BaseEntity {
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_no")
    private User writer;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_no")
    private Board board;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
}
