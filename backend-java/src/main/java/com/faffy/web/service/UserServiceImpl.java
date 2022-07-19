package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.mapping.PublicUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> findUserByNo(int no) {
        return userRepository.findByNo(no);
    }
    @Override
    public Optional<PublicUserInfo> findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public Optional<PublicUserInfo> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<PublicUserInfo> findAllUsers() {
        return userRepository.findAllBy();
    }


    @Override
    public Optional<User> updateUser(UserDto userDto) throws IllegalInputException {
        Optional<User> byNo = userRepository.findByNo(userDto.getNo());
        if (byNo.isPresent()) {
            User user = byNo.get();
            if (user.updateUser(userDto)) {
                userRepository.save(user);
                return Optional.of(user);
            } else
                throw new IllegalInputException("올바른 값을 입력해 주세요.");
        }

        return Optional.of(null);

    }

    @Override
    public void deleteUser(int no) throws DataNotFoundException{
        try {
            userRepository.deleteById(no);
        } catch (Exception e) {
            throw new DataNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
    }


    @Override
    public Optional<PublicUserInfo> login(UserDto userDto) {
        return userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
    }


    @Override
    public User addUser(UserDto userDto) throws DataIntegrityException {
            try {
                return userRepository.save(userDto.toEntity());
            } catch (Exception e) {
                throw new DataIntegrityException("중복된 값이 있는지 확인해 주세요.");
            }

    }


}
