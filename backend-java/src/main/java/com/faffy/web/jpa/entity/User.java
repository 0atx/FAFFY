package com.faffy.web.jpa.entity;

import com.faffy.web.dto.UserDto;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.type.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.time.LocalDate;
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
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;

    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    /**
     * 자기소개 문구
     */
    @Lob
    @Column(length = 2000)
    private String info;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "file_no")
    private UploadFile profileImage;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY, mappedBy = "user")
    @JsonIgnore
    List<Board> writeBoards = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY, mappedBy = "userCategoryMapper.user")
    @JsonIgnore
    List<UserCategory> categories = new ArrayList<>();

    public User() {
    }

    @Builder
    public User(int no, String email, String name, String nickname, String password, LocalDate birthday, Gender gender, String info) {
        this.no = no;
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
     *
     * @param userDto
     * @return 수정된 User
     * @throws IllegalInputException null 혹은 빈 값이면 안되는 입력이 들어올 경우
     */
    public void updateUser(UserDto userDto) throws IllegalInputException {
        this.nickname = userDto.getNickname();
        this.name = userDto.getName();
        this.password = userDto.getPassword();
        this.info = userDto.getInfo();
        this.birthday = LocalDate.parse(userDto.getBirthday());
    }

    public UserPublicDto toPublicDto() {
        UserPublicDto userPublicDto = UserPublicDto.builder()
                .no(this.no)
                .email(this.email)
                .nickname(this.nickname)
                .name(this.name)
                .gender(this.gender)
                .birthday(this.birthday)
                .info(this.info)
                .build();
        return userPublicDto;
    }

}
