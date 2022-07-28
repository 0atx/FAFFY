package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.dto.UserLoginDto;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.PublicUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.faffy.web.exception.ExceptionMsg.*;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public User getUserByNo(int no) throws Exception {
        return userRepository.findByNo(no).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
    }

    @Override
    public User getUserByNickname(String nickname) throws Exception {
        return userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
    }

    @Override
    public User getUserByEmail(String email) throws Exception{
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException(USER_NOT_FOUND_MSG));
    }

    @Override
    public List<PublicUserInfo> findAllUsers() {
        return userRepository.findAllBy();
    }


    @Override
    public void deleteUser(int no) throws Exception {
        try {
            userRepository.deleteById(no);
        } catch (Exception e) {
            throw new Exception(USER_NOT_FOUND_MSG);
        }
    }


    @Override
    public UserPublicDto login(UserLoginDto userDto) throws IllegalArgumentException {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(()->new IllegalArgumentException(USER_NOT_FOUND_MSG));

        if(!passwordEncoder.matches(userDto.getPassword(),user.getPassword())) {
            throw new IllegalArgumentException(LOGIN_FAILED_MSG);
        }
        return user.toPublicDto();
    }


    @Override
    public User addUser(UserDto userDto) throws IllegalInputException, DataIntegrityException {
        try {
            userDto.setRoles(Collections.singletonList("ROLE_USER"));
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            return userRepository.save(userDto.toEntity());
        } catch (IllegalInputException e) {
            throw new IllegalInputException(ILLEGAL_INPUT_MSG);
        } catch (Exception e) {
            throw new DataIntegrityException(DUPLICATED_MSG);
        }
    }

    @Override
    @Transactional
    public User updateUser(UserDto userDto) throws DataNotFoundException, IllegalInputException {
        try {
            User user;
            if (userDto.getNo() == 0) {
                user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
            } else {
                user = userRepository.findByNo(userDto.getNo()).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
            }
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.updateUser(userDto);
            return user;
        } catch (IllegalArgumentException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new IllegalInputException(ILLEGAL_INPUT_MSG);
        }
    }


}
