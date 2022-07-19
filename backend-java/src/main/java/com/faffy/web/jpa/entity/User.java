package com.faffy.web.jpa.entity;

import com.faffy.web.jpa.type.Gender;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : user table JPA entity
*/

@Entity
@Data
public class User extends BaseEntity {
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Gender gender;
    /**
     * 자기소개 문구
     */
    @Lob
    @Column(length=2000)
    private String info;
    @OneToOne @JoinColumn(name = "file_no")
    private UploadFile profileImage;


}
