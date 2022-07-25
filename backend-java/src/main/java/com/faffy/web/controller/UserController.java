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
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value="모든 회원 정보 받기",notes="공개정보를 리스트로 받아옵니다.")
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
    @ApiOperation(value="회원 가입",notes="유저 정보를 받아 db에 유저를 추가합니다.")
    @PostMapping
    public ResponseEntity addUser(@RequestBody UserDto userDto) {
        HashMap<String, Object> hashmap = new HashMap<>();
        HttpStatus status = null;
        try {
            User user = userService.addUser(userDto);
            hashmap.put("content",user);
            status = HttpStatus.OK;

        }
        catch (DataIntegrityException e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        catch (IllegalInputException e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(hashmap);
    }


    /**
     * no로 회원 찾기
     * @param no
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="no로 회원 찾기",notes="no에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/{no}")
    public ResponseEntity findUserByNo(@PathVariable("no") int no) {
        HashMap<String, Object> hashmap = new HashMap<>();
        HttpStatus status = null;
        try {
            User user = userService.getUserByNo(no);
            hashmap.put("content",user);
            status = HttpStatus.OK;

        }catch(Exception e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(hashmap);
    }

    /**
     * email로 회원 찾기
     * @param email
     * @return 성공시 회원 정보, 실패시 []
     */
    @ApiOperation(value="email로 회원 찾기",notes="email에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/email/{email}")
    public ResponseEntity findUserByEmail(@PathVariable("email") String email) {
        HashMap<String, Object> hashmap = new HashMap<>();
        HttpStatus status = null;
        try {
            User user = userService.getUserByEmail(email);
            hashmap.put("content",user);
            status = HttpStatus.OK;

        }catch(Exception e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(hashmap);
    }

    /**
     * nickname으로 유저 찾기
     * @param nickname
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="nickname으로 회원 찾기",notes="nickname에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity findUserByNickname(@PathVariable("nickname") String nickname) {
        HashMap<String, Object> hashmap = new HashMap<>();
        HttpStatus status = null;
        try {
            User user = userService.getUserByNickname(nickname);
            hashmap.put("content",user);
            status = HttpStatus.OK;
        }catch (Exception e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(hashmap);
    }

    /**
     * 회원정보 수정
     * @param userDto
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="회원정보 수장",notes="입력한 유저정보로 수정합니다. (바꾸지 않을 정보도 입력)")
    @PutMapping
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        HashMap<String, Object> hashmap = new HashMap<>();
        HttpStatus status = null;
        try {
            User user = userService.updateUser(userDto);
            hashmap.put("content",user);
            status = HttpStatus.OK;

        }catch (IllegalInputException e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;

        } catch (DataNotFoundException e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(hashmap);
    }

    /**
     * 회원 탈퇴
     * @param no
     * @return 성공시 no, 실패시 msg
     * @throws DataNotFoundException 수정하려는 일부 값이 유효하지 않을 경우
     */
    @ApiOperation(value="회원 탈퇴",notes="no에 해당하는 유저가 탈퇴됩니다.")
    @DeleteMapping("/{no}")
    public ResponseEntity deleteUser(@PathVariable int no) {
        HashMap<String, Object> hashmap = new HashMap<>();
        HttpStatus status = null;
        try {
            userService.deleteUser(no);
            hashmap.put("content",no);
            status = HttpStatus.OK;
        }catch (Exception e){
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(hashmap);
    }

    /**
     * 로그인
     * @param userDto
     * @return 성공시 토큰값(String), 실패시 msg
     * 토큰의 유효기간은 30분이고 request header에 'X-AUTH-TOKEN' : '토큰값' 형식으로 전송
     */
    @ApiOperation(value="로그인",notes="로그인시 토큰값(String)이 반환되고 이후 request header에 ‘X-AUTH-TOKEN’ : ‘토큰값’ 형식으로 전송 필요합니다.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userDto) {
        HashMap<String, Object> hashmap = new HashMap<>();
        HttpStatus status = null;
        try {
            UserPublicDto user = userService.login(userDto);
            hashmap.put("content", jwtTokenProvider.createToken(Integer.toString(user.getNo()), user.getRoles()));
            status = HttpStatus.OK;
        } catch(IllegalArgumentException e) {
            hashmap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(hashmap);
    }
}
