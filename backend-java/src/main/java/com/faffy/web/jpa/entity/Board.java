package com.faffy.web.jpa.entity;

import com.faffy.web.jpa.type.BoardCategory;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Board extends BaseEntity {
    @Column(nullable = false)
    private String title;

    private LocalDateTime datetime;
    @Lob
    @Column(length=2000, nullable = false)
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_no")
    private User user;
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true,fetch = EAGER)
    List<Comment> comments = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private BoardCategory category;

    public Board(){}
    @Builder
    public Board(int no, String title, LocalDateTime datetime, String content, User user,  BoardCategory category) {
        this.no = no;
        this.title = title;
        this.datetime = datetime;
        this.content = content;
        this.user = user;
        this.category = category;
    }
}
