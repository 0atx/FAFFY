package com.faffy.web.controller;

import com.faffy.web.dto.UserDto;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.PublicUserInfo;
import com.faffy.web.service.UserServiceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserApiController {

    @Autowired
    UserServiceImpl userService;

    /**
     * 모든 회원 정보 받아오기
     * @return 회원정보 리스트
     */
    @GetMapping
    public ResponseEntity findAllUsers() {
        List<PublicUserInfo> lst = userService.findAllUsers();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","모든 회원 리스트");
        jsonObject.put("result",lst);

        return ResponseEntity.ok().body(jsonObject);
    }
    /**
     * no로 회원 찾기
     * @param no
     * @return 성공시 회원 정보, 실패시 []
     */
    @GetMapping("/{no}")
    public ResponseEntity findUserByNo(@PathVariable("no") int no) {
        JSONObject jsonObject = new JSONObject();
        try {
            User user = userService.getUserByNo(no);
            jsonObject.put("msg","회원이 존재합니다.");
            jsonObject.put("result",user);
            return ResponseEntity.ok().body(jsonObject);

        }catch(Exception e) {
            jsonObject.put("msg",e.getMessage());
            jsonObject.put("result","[]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject);
        }
    }

    /**
     * email로 회원 찾기
     * @param email
     * @return 성공시 회원 정보, 실패시 []
     */
    @GetMapping("/email/{email}")
    public ResponseEntity findUserByEmail(@PathVariable("email") String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            User user = userService.getUserByEmail(email);
            jsonObject.put("msg","회원이 존재합니다.");
            jsonObject.put("result",user);
            return ResponseEntity.ok().body(jsonObject);
        }catch(Exception e) {
            jsonObject.put("msg",e.getMessage());
            jsonObject.put("result","[]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject);
        }
    }

    /**
     * nickname으로 유저 찾기
     * @param nickname
     * @return 성공시 회원 정보, 실패시 []
     */
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity findUserByNickname(@PathVariable("nickname") String nickname) {
        JSONObject jsonObject = new JSONObject();
        try {
            User user = userService.getUserByNickname(nickname);
            jsonObject.put("msg","회원이 존재합니다.");
            jsonObject.put("result",user);
            return ResponseEntity.ok().body(jsonObject);
        }catch (Exception e) {
            jsonObject.put("msg",e.getMessage());
            jsonObject.put("result","[]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject);
        }
    }

    /**
     * 회원정보 수정
     * @param userDto
     * @return 성공시 회원 정보, 실패시 []
     */
    @PutMapping
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        try {
            userService.updateUser(userDto);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg","정보 수정 완료");
            jsonObject.put("result","[]");
            return ResponseEntity.ok().body(jsonObject);

        }catch (IllegalInputException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",e.getMessage());
            jsonObject.put("result","[]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject);
        } catch (DataNotFoundException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("result", "[]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject);
        }
    }

    /**
     * 회원 탈퇴
     * @param no
     * @return 성공시 no, 실패시 []
     * @throws DataNotFoundException 수정하려는 일부 값이 유효하지 않을 경우
     */
    @DeleteMapping("/{no}")
    public ResponseEntity deleteUser(@PathVariable int no) {
        try {
            userService.deleteUser(no);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg","회원 삭제 완료");
            jsonObject.put("result",no);
            return ResponseEntity.ok().body(jsonObject);
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("result","[]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject);
        }
    }

}
