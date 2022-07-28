package com.faffy.web.controller;

import com.faffy.web.dto.UserCategoryRequestDto;
import com.faffy.web.dto.UserDto;
import com.faffy.web.dto.UserLoginDto;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.PublicUserInfo;
import com.faffy.web.service.UserCategoryService;
import com.faffy.web.service.UserServiceImpl;
import com.faffy.web.service.token.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserCategoryService userCategoryService;
    private final JwtTokenProvider jwtTokenProvider;
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);

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
    public ResponseEntity addUser(@Valid @RequestBody UserDto userDto) throws DataIntegrityException, IllegalInputException {
        logger.info("dto : {}",userDto);
        User user = userService.addUser(userDto);
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("content",user.toPublicDto());
        return ResponseEntity.ok().body(hashmap);
    }


    /**
     * no로 회원 찾기
     * @param no
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="no로 회원 찾기",notes="no에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/{no}")
    public ResponseEntity findUserByNo(@PathVariable("no") int no) throws Exception {
        User user = userService.getUserByNo(no);
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("content",user);
        return ResponseEntity.ok().body(hashmap);
    }

    /**
     * email로 회원 찾기
     * @param email
     * @return 성공시 회원 정보, 실패시 []
     */
    @ApiOperation(value="email로 회원 찾기",notes="email에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/email/{email}")
    public ResponseEntity findUserByEmail(@PathVariable("email") String email) throws Exception {
        User user = userService.getUserByEmail(email);
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("content",user);
        return ResponseEntity.ok().body(hashmap);
    }

    /**
     * nickname으로 유저 찾기
     * @param nickname
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="nickname으로 회원 찾기",notes="nickname에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity findUserByNickname(@PathVariable("nickname") String nickname) throws Exception {
        User user = userService.getUserByNickname(nickname);
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("content",user);
        return ResponseEntity.ok().body(hashmap);
    }

    /**
     * 회원정보 수정
     * @param userDto
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="회원정보 수장",notes="입력한 유저정보로 수정합니다. (바꾸지 않을 정보도 입력)")
    @PutMapping
    public ResponseEntity updateUser(@RequestBody UserDto userDto) throws DataNotFoundException, IllegalInputException {
        User user = userService.updateUser(userDto);
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("content",user);
        return ResponseEntity.ok().body(hashmap);
    }

    /**
     * 회원 탈퇴
     * @param no
     * @return 성공시 no, 실패시 msg
     * @throws DataNotFoundException 수정하려는 일부 값이 유효하지 않을 경우
     */
    @ApiOperation(value="회원 탈퇴",notes="no에 해당하는 유저가 탈퇴됩니다.")
    @DeleteMapping("/{no}")
    public ResponseEntity deleteUser(@PathVariable int no) throws Exception {
        userService.deleteUser(no);
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("content",no);
        return ResponseEntity.ok().body(hashmap);
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
        UserPublicDto user = userService.login(userDto);
        HashMap<String, Object> hashmap = new HashMap<>();
        HashMap<String, Object> hashmapIn = new HashMap<>();
        hashmapIn.put("UserPublicDto",user);
        hashmapIn.put("Token",jwtTokenProvider.createToken(Integer.toString(user.getNo()), user.getRoles()));
        hashmap.put("content", hashmapIn);
        return ResponseEntity.ok().body(hashmap);
    }

    @PostMapping("/category")
    public ResponseEntity addCategory(@RequestBody UserCategoryRequestDto userCategoryAddDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            String result = userCategoryService.addUserCategory(userCategoryAddDto.getUser_no(), userCategoryAddDto.getCategory_name());
            resultMap.put("content", result);
        } catch (Exception e) {
            logger.error("카테고리 추가 에러 발생 : {}",e.getMessage());
            resultMap.put("msg", e.getMessage());
        }

        return new ResponseEntity(resultMap,status);
    }

    @DeleteMapping("/category")
    public ResponseEntity deleteCategory(@RequestBody UserCategoryRequestDto userCategoryRequestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            String result = userCategoryService.deleteUserCategory(userCategoryRequestDto.getUser_no(), userCategoryRequestDto.getCategory_name());
            resultMap.put("content", result);
        } catch (Exception e) {
            logger.error("카테고리 삭제 에러 발생 : {}",e.getMessage());
            resultMap.put("msg", e.getMessage());
        }

        return new ResponseEntity(resultMap,status);
    }

}
