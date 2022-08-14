package com.faffy.web.service.auth;

import com.faffy.web.config.auth.dto.NaverOAuthToken;
import com.faffy.web.config.auth.dto.NaverUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@CrossOrigin("*")
public class NaverOauth{
    @Value("${spring.security.oauth2.client.provider.naver.authorization_uri}")
    private String NAVER_SNS_LOGIN_URL;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_SNS_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String NAVER_SNS_CALLBACK_URL;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_SNS_CLIENT_SECRET;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public Map<String, String> getOauthRedirectURL() {
        Map<String, String> resultMap = new HashMap<>();
        Map<String,Object> params = new HashMap<>();
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        params.put("response_type","code");
        params.put("client_id",NAVER_SNS_CLIENT_ID);
        params.put("redirect_uri",NAVER_SNS_CALLBACK_URL);
        params.put("state", state);

        String parameterString=params.entrySet().stream()
                .map(x->x.getKey()+"="+x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL=NAVER_SNS_LOGIN_URL+"?"+parameterString;
        System.out.println("redirectURL = " + redirectURL);

        resultMap.put("redirectURL", redirectURL);
        resultMap.put("state", state);
        return resultMap;
    }

    public ResponseEntity<String> requestAccessToken(String code, String state){
        String NAVER_TOKEN_REQUEST_URL = "https://nid.naver.com/oauth2.0/token";
        RestTemplate restTemplate=new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", NAVER_SNS_CLIENT_ID);
        params.put("client_secret", NAVER_SNS_CLIENT_SECRET);
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("state", state);

        String parameterString=params.entrySet().stream()
                .map(x->x.getKey()+"="+x.getValue())
                .collect(Collectors.joining("&"));
        String requestURL = NAVER_TOKEN_REQUEST_URL + "?" + parameterString;

        ResponseEntity<String> responseEntity=restTemplate.getForEntity(requestURL, String.class);

        if(responseEntity.getStatusCode()== HttpStatus.OK){
            return responseEntity;
        }
        return null;
    }

    public NaverOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() = " + response.getBody());
        NaverOAuthToken naverOAuthToken= objectMapper.readValue(response.getBody(),NaverOAuthToken.class);
        return naverOAuthToken;
    }

    public ResponseEntity<String> requestUserInfo(NaverOAuthToken oAuthToken) {
        String NAVER_USERINFO_REQUEST_URL="https://openapi.naver.com/v1/nid/me";

        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        System.out.println("헤더에 Access Token 정보 담아 네이버에 회원 정보 요청");
        ResponseEntity<String> response=restTemplate.exchange(NAVER_USERINFO_REQUEST_URL, HttpMethod.GET,request,String.class);
        System.out.println("response.getBody() = " + response.getBody());
        return response;
    }

    public NaverUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(userInfoRes.getBody());
        NaverUser naverUser=objectMapper.readValue(jsonObject.get("response").toString(), NaverUser.class);
        return naverUser;
    }
}
