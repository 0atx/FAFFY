package com.faffy.web.service.auth;

import com.faffy.web.config.auth.dto.GetSocialOAuthRes;
import com.faffy.web.config.auth.dto.GoogleOAuthToken;
import com.faffy.web.config.auth.dto.GoogleUser;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.LoginType;
import com.faffy.web.jpa.type.SocialLoginType;
import com.faffy.web.service.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthService {
    @Autowired
    UserRepository userRepository;
    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
    private final JwtTokenProvider jwtTokenProvider;

    public String request(SocialLoginType socialLoginType) throws IOException {
        String redirectURL;
        switch (socialLoginType){
            case GOOGLE:{
                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트
                redirectURL= googleOauth.getOauthRedirectURL();
            }break;
            default:{
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }

        System.out.println("구글 로그인으로 리다이렉트");
//        response.sendRedirect(redirectURL);
        return redirectURL;
    }

    public GetSocialOAuthRes oAuthLogin(SocialLoginType socialLoginType, String code) throws Exception {

        switch (socialLoginType){
            case GOOGLE:{
                //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
                ResponseEntity<String> accessTokenResponse= googleOauth.requestAccessToken(code);
                //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
                GoogleOAuthToken oAuthToken=googleOauth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
                ResponseEntity<String> userInfoResponse=googleOauth.requestUserInfo(oAuthToken);
                //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
                GoogleUser googleUser= googleOauth.getUserInfo(userInfoResponse);
                System.out.println(googleUser);

                String user_id=googleUser.getEmail();
                //우리 서버의 db와 대조하여 해당 user가 존재하는 지 확인한다.
                User user = userRepository.findByEmailAndLoginType(user_id, LoginType.GOOGLE).orElse(null);
//                int user_num=accountProvider.getUserNum(user_id);

                GetSocialOAuthRes res = null;
                if(user != null) {
                    //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
                    String jwtToken = jwtTokenProvider.createToken(String.valueOf(user.getNo()), user.getRoles());
                    System.out.println("JWT Token created");
                    //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
                    res = new GetSocialOAuthRes(jwtToken, user.getNo(), oAuthToken.getAccess_token(), oAuthToken.getToken_type(), true, user);
                }else {
//                    throw new Exception("Account does not exists.");
                    System.out.println("--------소셜 회원가입--------");

                    List<String> roles = new ArrayList<>();
                    roles.add("ROLE_USER");
                    User newUser = User.builder()
                            .email(googleUser.getEmail())
                            .name(googleUser.getFamily_name() + googleUser.getGiven_name())
                            .nickname(googleUser.getName())
                            .roles(roles)
                            .loginType(LoginType.GOOGLE)
                            .build();
                    userRepository.save(newUser);

                    String jwtToken = jwtTokenProvider.createToken(String.valueOf(newUser.getNo()), newUser.getRoles());
                    res = new GetSocialOAuthRes(jwtToken, newUser.getNo(), oAuthToken.getAccess_token(), oAuthToken.getToken_type(), false, newUser);
                }
                return res;
            }
            default:{
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
    }

}
