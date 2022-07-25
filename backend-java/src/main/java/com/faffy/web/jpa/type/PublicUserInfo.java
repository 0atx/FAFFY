package com.faffy.web.jpa.type;

import com.faffy.web.jpa.entity.UserCategory;

import java.time.LocalDate;
import java.util.List;

public interface PublicUserInfo {
    int getNo();
    String getName();
    String getEmail();
    String getNickname();
    LocalDate getBirthday();
    String getInfo();
    Gender getGender();
}
