package com.faffy.web.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class UploadFile extends BaseEntity {
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "upload_path")
    private String uploadPath;

    private String uuid;
}
