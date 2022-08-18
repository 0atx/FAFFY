package com.faffy.web.mapping;

import com.faffy.web.jpa.type.Gender;
import lombok.ToString;

import java.util.Date;

public interface PublicUserInfo {
    int getNo();
    String getName();
    String getEmail();
    String getNickname();
    Date getBirthday();
    String getInfo();
    Gender getGender();
}
