package com.faffy.web.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Table(name = "board_file")
public class BoardFile extends BaseEntity {
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "board_no")
    private Board board;
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "file_no")
    private UploadFile file;

}
