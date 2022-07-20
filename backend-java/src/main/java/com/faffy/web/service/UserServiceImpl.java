package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.mapping.PublicUserInfo;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> getUserByNo(int no) {
        return userRepository.findByNo(no);
    }

    @Override
    public Optional<PublicUserInfo> getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public Optional<PublicUserInfo> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<PublicUserInfo> findAllUsers() {
        return userRepository.findAllBy();
    }


    @Override
    public void deleteUser(int no) throws DataNotFoundException {
        try {
            userRepository.deleteById(no);
        } catch (Exception e) {
            throw e;
//            throw new DataNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
    }


    @Override
    public Optional<PublicUserInfo> login(UserDto userDto) {
        return userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
    }


    @Override
    @Transactional
    public User addUser(UserDto userDto) throws DataIntegrityException {
        try {
            return userRepository.save(userDto.toEntity());
        } catch (Exception e) {
            throw new DataIntegrityException("이미 사용중인 이메일이나 닉네임입니다. 확인해 주세요.");
        }

    }

    @Override
    @Transactional
    public int updateUser(UserDto userDto) {
        User user = userRepository.findByNo(userDto.getNo()).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        user.updateUser(userDto);
        return userDto.getNo();
    }


}
