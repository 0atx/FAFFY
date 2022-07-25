package com.faffy.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Board extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Lob
    @Column(length=2000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "writer_no")
    private User user;
    @Enumerated(EnumType.STRING)
    private BoardCategory category;
}
