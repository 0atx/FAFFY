package com.faffy.web.jpa.type;

import java.time.LocalDate;

public interface PublicUserInfo {
    int getNo();
    String getName();
    String getEmail();
    String getNickname();
    LocalDate getBirthday();
    String getInfo();
    Gender getGender();
    LoginType getLoginType();
    String getInstaLink();
    String getFacebookLink();
    String getYoutubeLink();
}
