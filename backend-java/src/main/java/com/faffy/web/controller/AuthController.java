package com.faffy.web.controller;

import com.faffy.web.config.auth.dto.GetSocialOAuthRes;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.SocialLoginType;
import com.faffy.web.service.UserService;
import com.faffy.web.service.auth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    @Autowired
    OAuthService oAuthService;
    @Autowired
    UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private static final String mainURL = "http://localhost:8080/";

    /**
     * 소셜 로그인 - 해당 소셜 로그인 페이지(구글/네이버/카카오)로 리다이렉트
     * @param socialLoginType
     * @throws IOException
     */
    @GetMapping("/login/{socialLoginType}")
    public ResponseEntity<String> socialLoginRedirect(@PathVariable String socialLoginType) throws IOException {
        SocialLoginType type= SocialLoginType.valueOf(socialLoginType.toUpperCase());
        return new ResponseEntity<>(oAuthService.request(type), HttpStatus.OK);
    }

    @GetMapping("/login/{socialLoginType}/callback")
    public ResponseEntity callback (
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws Exception {
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        SocialLoginType socialLoginType= SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes=oAuthService.oAuthLogin(socialLoginType,code);

        //토큰, 유저 정보를 담아 홈으로 리다이렉트
        UserPublicDto user = getSocialOAuthRes.getUser().toPublicDto();
        String token = getSocialOAuthRes.getJwtToken();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", user);
        resultMap.put("token", token);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
