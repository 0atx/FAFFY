package com.faffy.web.controller;

import com.faffy.web.dto.UserDto;
import com.faffy.web.dto.UserLoginDto;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.UserNoAndNicknameMask;
import com.faffy.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test/users")
@CrossOrigin("*")
public class TestController {
    @Autowired
    UserService userService;
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);



    @PostMapping("/login")
    public ResponseEntity login(UserLoginDto userLoginDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            UserPublicDto login = userService.login(userLoginDto);
            System.out.println(login);
            resultMap.put("content", login);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("로그인 실패 : {}", e.getMessage());
            logger.error("입력 : {}, {}", userLoginDto.getEmail(),userLoginDto.getPassword());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity(resultMap, status);
    }

    @PostMapping("")
    public ResponseEntity addUser(UserDto userDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            UserPublicDto userPublicDto = userService.addUser(userDto).toPublicDto();
            resultMap.put("content",userPublicDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("회원가입 실패 : {}",e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap,status);
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteUser(UserDto userDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            userService.deleteUser(userDto.getNo());
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("회원탈퇴 실패 : {}",e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap,status);
        }
    }

    @GetMapping("")
    public ResponseEntity getAllUsers() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<UserNoAndNicknameMask> allUsers = userService.findAllUsers();
            resultMap.put("content", allUsers);
        } catch (Exception e) {
            logger.error("회원 목록 찾기 실패 {}",e.toString());
            resultMap.put("msg",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(resultMap,status);
    }

    @GetMapping("/{no}")
    public ResponseEntity getUser(@PathVariable("no") int no) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            User user = userService.getUserByNo(no);
            resultMap.put("content", user.toPublicDto());
        } catch (Exception e) {
            logger.error("회원 정보 찾기 실패 {}",e.toString());
            resultMap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap,status);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable("email") String email) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            User user = userService.getUserByEmail(email);
            resultMap.put("content", user.toPublicDto());
        } catch (Exception e) {
            logger.error("회원 정보 찾기 실패 {}",e.toString());
            resultMap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap,status);
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity getUserByNickname(@PathVariable("nickname") String nickname) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            User user = userService.getUserByNickname(nickname);
            resultMap.put("content", user.toPublicDto());
        } catch (Exception e) {
            logger.error("회원 정보 찾기 실패 {}",e.toString());
            resultMap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap,status);
    }

    @PutMapping
    public ResponseEntity updateUser(UserDto userDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            User user = userService.updateUser(userDto);
            resultMap.put("content", user.toPublicDto());
        } catch (Exception e) {
            logger.error("회원 정보 수정 실패 {}",e.toString());
            resultMap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap,status);
    }
}
