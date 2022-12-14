package com.faffy.web.jpa.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "consulting_file")
public class ConsultingFile extends BaseEntity {
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "consulting_no")
    private Consulting consulting;
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "file_no")
    private UploadFile file;

    public ConsultingFile() {}
    @Builder
    public ConsultingFile(Consulting consulting,UploadFile file) {
        this.consulting = consulting;
        this.file = file;
    }
}
