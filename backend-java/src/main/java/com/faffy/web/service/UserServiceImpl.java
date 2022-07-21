package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.dto.UserLoginDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.PublicUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.faffy.web.exception.ExceptionMsg.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;



    @Override
    public User getUserByNo(int no) throws Exception {
        return userRepository.findByNo(no).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
    }

    @Override
    public User getUserByNickname(String nickname) throws Exception {
        return userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
    }

    @Override
    public User getUserByEmail(String email) {
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
    public PublicUserInfo login(UserLoginDto userDto) throws Exception {
        return userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword())
                .orElseThrow(() -> new IllegalArgumentException(LOGIN_FAILED_MSG));
    }


    @Override
    public User addUser(UserDto userDto) throws DataIntegrityException {
        try {
            return userRepository.save(userDto.toEntity());
        } catch (Exception e) {
            throw new DataIntegrityException("이미 사용중인 이메일이나 닉네임입니다. 확인해 주세요.");
        }

    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) throws DataNotFoundException, IllegalInputException {
        try {
            User user;
            if (userDto.getNo() == 0) {
                user = userRepository.findByNickname(userDto.getNickname()).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
            } else {
                user = userRepository.findByNo(userDto.getNo()).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MSG));
            }

            user.updateUser(userDto);
        } catch (IllegalArgumentException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new IllegalInputException(ILLEGAL_INPUT_MSG);
        }
    }


}
