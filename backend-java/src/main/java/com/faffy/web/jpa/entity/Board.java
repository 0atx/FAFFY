package com.faffy.web.jpa.entity;

import com.faffy.web.dto.BoardGetDto;
import com.faffy.web.dto.BoardUpdateDto;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.type.BoardCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Board extends BaseEntity {
    @Column(nullable = false)
    private String title;

    private LocalDateTime datetime;
    @Lob
    @Column(length = 2000, nullable = false)
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_no")
    private User user;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private BoardCategory category;

    private int hit;
    private int commentCount;

    public Board() {
    }

    @Builder
    public Board(int no, String title, LocalDateTime datetime, String content, User user, BoardCategory category,
                 int hit, int commentCount) {
        this.no = no;
        this.title = title;
        this.datetime = datetime;
        this.content = content;
        this.user = user;
        this.category = category;
        this.hit = hit;
        this.commentCount = commentCount;
    }

    public void updateBoard(BoardUpdateDto boardDto) throws IllegalInputException {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
        this.category = boardDto.getCategory();
        this.datetime = LocalDateTime.now();
    }

    public BoardGetDto toBoardGetDto() {
        BoardGetDto boardGetDto = BoardGetDto.builder()
                .no(getNo())
                .title(getTitle())
                .content(getContent())
                .dateTime(getDatetime().toString())
                .user(getUser().toPublicDto())
                .hit(getHit())
                .commentCount(getCommentCount())
                .build();

        return boardGetDto;
    }

    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.addTo(this);
        this.commentCount = this.getComments().size();
    }

    public void increaseHit() {
        this.hit++;
    }
}
