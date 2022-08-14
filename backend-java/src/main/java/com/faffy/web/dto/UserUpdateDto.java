package com.faffy.web.dto;

import com.faffy.web.jpa.type.Gender;
import com.faffy.web.jpa.type.LoginType;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import static com.faffy.web.exception.ExceptionMsg.ILLEGAL_PASSWORD_CONDITION;
import static com.faffy.web.jpa.type.RegularExpression.PASSWORD_REG_EX;

@Data
public class UserUpdateDto {
    private int no;
    @NonNull
    @Email(message="이메일 형식이 올바르지 않습니다.ex)***@***.***")
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String nickname;

    @Pattern(regexp = PASSWORD_REG_EX,message = ILLEGAL_PASSWORD_CONDITION)
    private String password;
    @NonNull
    private String birthday;
    @NonNull
    private String gender;

    private List<String> roles = new ArrayList<>();

    private String introduce;
    /**
     * 자기소개 문구
     */
    private String info;
    @NonNull
    private LoginType loginType;

    private String instaLink;
    private String facebookLink;
    private String youtubeLink;
    private List<String> categories = new ArrayList<>();
    /**
     * 프로필 사진
     */
    private MultipartFile file;

    public UserUpdateDto(){};

}
