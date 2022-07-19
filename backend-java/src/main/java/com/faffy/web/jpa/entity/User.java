package com.faffy.web.jpa.entity;

import com.faffy.web.dto.UserDto;
import com.faffy.web.jpa.type.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;

/*
작성자 : 이준성
기능 : #S07P12A802-147-[BE] JPA Entity 구현
설명 : user table JPA entity
*/

@Entity
@Getter
@ToString
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
    @OneToOne(fetch = LAZY) @JoinColumn(name = "file_no")
    private UploadFile profileImage;


    public User() {

    }

    @Builder
    public User(String email, String name, String nickname, String password, Date birthday, Gender gender, String info) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.info = info;
    }

    /**
     * 이름, 닉네임, 패스워드, 자기소개, 생년월일 수정
     * @param userDto
     * @return 수정된 User
     */
    public boolean updateUser(UserDto userDto) {
        if (!userDto.isValid())
            return false;

        this.nickname = userDto.getNickname();
        this.name=userDto.getName();
        this.password=userDto.getPassword();
        this.info=userDto.getInfo();
        this.birthday = userDto.getBirthday();

        return true;
    }
}
