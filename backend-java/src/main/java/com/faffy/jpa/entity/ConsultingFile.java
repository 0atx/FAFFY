package com.faffy.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "consulting_file")
public class ConsultingFile extends BaseEntity {
    @ManyToOne @JoinColumn(name = "consulting_no")
    private Consulting consulting;
    @ManyToOne @JoinColumn(name = "file_no")
    private UploadFile file;
}
