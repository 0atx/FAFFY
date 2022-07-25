package com.faffy.web.controller;

import com.faffy.web.dto.UserDto;
import com.faffy.web.dto.UserLoginDto;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.PublicUserInfo;
import com.faffy.web.service.UserServiceImpl;
import com.faffy.web.service.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 모든 회원 정보 받아오기
     * @return 회원정보 리스트
     */
    @GetMapping
    public ResponseEntity<Map<String,Object>> findAllUsers() {
        Map<String, Object> resultMap = new HashMap<>();
        List<PublicUserInfo> allUsers = userService.findAllUsers();
        resultMap.put("users",allUsers);

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    /**
     * 회원가입
     * @param userDto
     * @return 성공 시 User, 실패시 msg
     */
    @PostMapping
    public ResponseEntity addUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.addUser(userDto);
            HashMap<String, User> hashmap = new HashMap<>();
            hashmap.put("content",user);

            return ResponseEntity.ok().body(hashmap);
        }
        catch (DataIntegrityException e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        }
        catch (IllegalInputException e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        }
    }


    /**
     * no로 회원 찾기
     * @param no
     * @return 성공시 User, 실패시 msg
     */
    @GetMapping("/{no}")
    public ResponseEntity findUserByNo(@PathVariable("no") int no) {
        try {
            User user = userService.getUserByNo(no);
            HashMap<String, User> hashmap = new HashMap<>();
            hashmap.put("content",user);
            return ResponseEntity.ok().body(hashmap);

        }catch(Exception e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        }
    }

    /**
     * email로 회원 찾기
     * @param email
     * @return 성공시 회원 정보, 실패시 []
     */
    @GetMapping("/email/{email}")
    public ResponseEntity findUserByEmail(@PathVariable("email") String email) {
        try {
            User user = userService.getUserByEmail(email);
            HashMap<String, User> hashmap = new HashMap<>();
            hashmap.put("content",user);
            return ResponseEntity.ok().body(hashmap);
        }catch(Exception e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        }
    }

    /**
     * nickname으로 유저 찾기
     * @param nickname
     * @return 성공시 User, 실패시 msg
     */
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity findUserByNickname(@PathVariable("nickname") String nickname) {
        try {
            User user = userService.getUserByNickname(nickname);
            HashMap<String, User> hashmap = new HashMap<>();
            hashmap.put("content",user);
            return ResponseEntity.ok().body(user);
        }catch (Exception e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        }
    }

    /**
     * 회원정보 수정
     * @param userDto
     * @return 성공시 User, 실패시 msg
     */
    @PutMapping
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.updateUser(userDto);
            HashMap<String, User> hashmap = new HashMap<>();
            hashmap.put("content",user);
            return ResponseEntity.ok().body(hashmap);

        }catch (IllegalInputException e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        } catch (DataNotFoundException e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        }
    }

    /**
     * 회원 탈퇴
     * @param no
     * @return 성공시 no, 실패시 msg
     * @throws DataNotFoundException 수정하려는 일부 값이 유효하지 않을 경우
     */
    @DeleteMapping("/{no}")
    public ResponseEntity deleteUser(@PathVariable int no) {
        try {
            userService.deleteUser(no);
            HashMap<String, Integer> hashmap = new HashMap<>();
            hashmap.put("content",no);
            return ResponseEntity.ok().body(hashmap);
        }catch (Exception e){
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        }
    }

    /**
     * 로그인
     * @param userDto
     * @return 성공시 토큰값(String), 실패시 msg
     * 토큰의 유효기간은 30분이고 request header에 'X-AUTH-TOKEN' : '토큰값' 형식으로 전송
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userDto) {
        try {
            UserPublicDto user = userService.login(userDto);
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("content", jwtTokenProvider.createToken(user.getEmail(), user.getRoles()));
            return ResponseEntity.ok().body(hashmap);
        } catch(IllegalArgumentException e) {
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
