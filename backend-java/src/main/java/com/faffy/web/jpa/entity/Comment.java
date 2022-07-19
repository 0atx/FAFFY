package com.faffy.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Comment extends BaseEntity {
    @Column(nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn(name = "writer_no")
    private User writer;
    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
}
