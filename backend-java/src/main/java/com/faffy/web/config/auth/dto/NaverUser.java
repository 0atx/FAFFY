package com.faffy.web.config.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class NaverUser {
    private String id;
    private String email;
    private String name;
    private String gender;
    private String birthday;
    private String birthyear;
}
