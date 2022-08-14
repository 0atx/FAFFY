package com.faffy.web.config.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class NaverOAuthToken {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
}
