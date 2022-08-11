package com.faffy.web.config.auth.dto;

import com.faffy.web.jpa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetSocialOAuthRes {
    private String jwtToken;
    private int user_num;
    private String accessToken;
    private String tokenType;
    private boolean Registered;
    private User user;
}
