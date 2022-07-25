package com.faffy.web.service.auth;

import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.faffy.web.exception.ExceptionMsg.USER_NOT_FOUND_MSG;

/*
작성자 : 이용우
기능 : #S07P12A802-4-[BE] 회원가입 기능
설명 : 인증관리를 위한 서비스
*/
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }
}

