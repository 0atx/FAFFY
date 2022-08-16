package com.faffy.web.controller;

import com.faffy.web.dto.*;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.type.SocialLoginType;
import com.faffy.web.jpa.type.UserNoAndNicknameMask;
import com.faffy.web.service.*;
import com.faffy.web.service.auth.OAuthService;
import com.faffy.web.service.token.JwtTokenProvider;
import com.sun.net.httpserver.HttpsServer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserCategoryServiceImpl userCategoryService;
    @Autowired
    ConsultingService consultingService;
    @Autowired
    BoardService boardService;
    @Autowired
    EmailService emailService;

    private final JwtTokenProvider jwtTokenProvider;
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 로그인
     * @param userDto
     * @return 성공시 토큰값(String), 실패시 msg
     * 토큰의 유효기간은 30분이고 request header에 'X-AUTH-TOKEN' : '토큰값' 형식으로 전송
     */
    @ApiOperation(value="로그인",notes="로그인시 토큰값(String)이 반환되고 이후 request header에 ‘X-AUTH-TOKEN’ : ‘토큰값’ 형식으로 전송 필요합니다.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userDto) {
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> resultMapIn = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            UserPublicDto user = userService.login(userDto);
            resultMapIn.put("user", user);
            resultMapIn.put("token", jwtTokenProvider.createToken(Integer.toString(user.getNo()), user.getRoles()));
            resultMap.put("content", resultMapIn);
        } catch (Exception e) {
            logger.error("로그인 에러 : {}",e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap, status);
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(value = "X-AUTH-TOKEN") String token) {
        userService.logout(token);
    }

    /**
     * 모든 회원 정보 받아오기
     * @return 회원정보 리스트
     */
    @ApiOperation(value="모든 회원 정보 받기",notes="공개정보를 리스트로 받아옵니다.")
    @GetMapping
    public ResponseEntity<Map<String,Object>> findAllUsers() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        List<UserNoAndNicknameMask> allUsers = userService.findAllUsers();
        resultMap.put("content",allUsers);

        return new ResponseEntity(resultMap,status);
    }
    /**
     * 회원가입
     * @param userDto
     * @return 성공 시 User, 실패시 msg
     */
    @ApiOperation(value="회원 가입",notes="유저 정보를 받아 db에 유저를 추가합니다.")
    @PostMapping("")
    public ResponseEntity addUser(@Valid @RequestBody UserDto userDto) {
        logger.info("user dto : {}",userDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User user = userService.addUser(userDto);
            resultMap.put("content",user.toPublicDto());
        } catch (Exception e) {
            logger.error("회원 가입 에러 : {}", e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap, status);
        }
    }


    /**
     * no로 회원 찾기
     * @param no
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="no로 회원 찾기",notes="no에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/{no}")
    public ResponseEntity findUserByNo(@PathVariable("no") int no) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User user = userService.getUserByNo(no);
            resultMap.put("content", user.toDetailDto());
        } catch (Exception e) {
            logger.error("유저 찾기 실패 : {}", e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap, status);
        }
    }

    /**
     * email로 회원 찾기
     * @param email
     * @return 성공시 회원 정보, 실패시 []
     */
    @ApiOperation(value="email로 회원 찾기",notes="email에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/email/{email}")
    public ResponseEntity findUserByEmail(@PathVariable("email") String email) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User user = userService.getUserByEmail(email);
            resultMap.put("content",user.toDetailDto());
        } catch (Exception e) {
            logger.error("회원 정보 찾기 에러 : {}",e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap, status);
        }
    }

    /**
     * nickname으로 유저 찾기
     * @param nickname
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="nickname으로 회원 찾기",notes="nickname에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity findUserByNickname(@PathVariable("nickname") String nickname) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User user = userService.getUserByNickname(nickname);
            resultMap.put("content", user.toDetailDto());
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap, status);
        }
    }

    @ApiOperation(value="JWT 토큰으로 회원 찾기",notes="jwt 토큰에 해당하는 유저의 정보를 반환합니다.")
    @GetMapping("/token")
    public ResponseEntity findUserByToken(@RequestParam String token) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User user = userService.getUserByNo(Integer.parseInt(jwtTokenProvider.getUserPk(token)));
            resultMap.put("user", user);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap, status);
        }
    }

    @ApiOperation(value="비밀번호 찾기",notes="해당 유저의 이메일로 임시 비밀번호를 전송합니다.")
    @PutMapping("/findpwd")
    public ResponseEntity<Map<String, Object>> findPwd(@RequestParam String email, @RequestParam String birthday) {
        HttpStatus status = null;
        Map<String, Object> resultMap = new HashMap<>();
        String pwd = "";

        LocalDate date = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
        User user = userService.getUserByEmailBirthday(email, date);
        if(user == null){
            resultMap.put("msg", "해당 이메일, 생일과 일치하는 유저가 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
        try {
            pwd = emailService.sendMsg(email);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("msg", "해당 이메일로의 비밀번호 전송에 실패하였습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        if(userService.setUserPwdByUserEmail(email, pwd)) {
            status = HttpStatus.OK;
        } else {
            resultMap.put("msg", "존재하지 않는 유저입니다.");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value="회원 프로필 정보 조회", notes="해당 유저의 프로필 사진을 제외한 프로필 정보를 반환합니다.")
    @GetMapping("/profile/{no}")
    public ResponseEntity<UserGetDetailDto> getUserProfile(@PathVariable int no) {
        HttpStatus status = HttpStatus.OK;

        UserGetDetailDto dto = userService.getProfile(no);
        if(dto == null){
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(dto, status);
    }

    @ApiOperation(value="회원 프로필 사진 조회", notes="해당 유저의 프로필 사진을 반환합니다.")
    @GetMapping("/profile/image/{no}")
    public ResponseEntity<byte[]> getUserProfileImg(@PathVariable int no) {
        try {
            File file = userService.getProfileImg(no);
            if(file == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            HttpHeaders header = new HttpHeaders();
            try {
                header.add("Content-Type", Files.probeContentType(file.toPath()));
                return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalInputException e) {
            System.out.println("프로필 이미지 불러오기 실패!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/profile/image/{no}")
    public ResponseEntity deleteUserProfileImg(@PathVariable int no) {
        HttpStatus status = HttpStatus.OK;
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            userService.deleteUserImg(no);
            resultMap.put("content",no);
        } catch (DataNotFoundException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("msg",e.getMessage());
        }
        return new ResponseEntity(resultMap, status);
    }

    /**
     * 회원정보 수정
     * @param userDto
     * @return 성공시 User, 실패시 msg
     */
    @ApiOperation(value="회원정보 수정",notes="입력한 유저정보로 수정합니다. (바꾸지 않을 정보도 입력)", produces = "multipart/form-data")
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateUser(@ModelAttribute UserUpdateDto userDto) {
        logger.info("user dto : {}",userDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User user = userService.updateUser(userDto);
            userCategoryService.setUserCategories(user, userDto.getCategories());

            resultMap.put("content", user.toDetailDto());
        } catch(Exception e) {
            logger.error("정보 수정 에러 발생 : {}",e.getMessage());
            resultMap.put("msg","입력 값을 확인해 주세요.");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap,status);
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
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            deleteUserProfileImg(no);
            userService.deleteUser(no);
            resultMap.put("content", no);
        } catch (Exception e) {
            logger.error("회원 탈퇴 에러 : {}",e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } finally {
            return new ResponseEntity(resultMap, status);
        }
    }

    @ApiOperation(value="방송 참여/진행 기록 최신순으로 불러오기", notes="해당 유저의 방송 기록을 최신순으로 반환한다")
    @GetMapping("/profile/{no}/history/date")
    public ResponseEntity<Map<String, Object>> getConsultingHistoryByDate(@PathVariable int no,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size){
        HashMap<String, Object> map = new HashMap<>();
        List<BroadCastHistoryDto> dtoList = userService.getConsultHistoryByDate(no);
        if(dtoList == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());
            int start = (int)paging.getOffset();
            int end = Math.min(start+paging.getPageSize(), dtoList.size());
            Page<BroadCastHistoryDto> res = new PageImpl<>(dtoList.subList(start, end), paging, dtoList.size());
            map.put("content", res.getContent());
            map.put("currentPage", res.getNumber());
            map.put("totalItems", res.getTotalElements());
            map.put("totalPages", res.getTotalPages());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @ApiOperation(value="방송 참여 기록 불러오기", notes="해당 유저의 방송 참여 기록을 반환")
    @GetMapping("/profile/{no}/history/parti")
    public ResponseEntity<Map<String, Object>> getParticipantHistory(@PathVariable int no,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size){
        HashMap<String, Object> map = new HashMap<>();
        List<BroadCastHistoryDto> dtoList = userService.getPartiList(no);
        if(dtoList == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());
            int start = (int)paging.getOffset();
            int end = Math.min(start+paging.getPageSize(), dtoList.size());
            Page<BroadCastHistoryDto> res = new PageImpl<>(dtoList.subList(start, end), paging, dtoList.size());
            map.put("content", res.getContent());
            map.put("currentPage", res.getNumber());
            map.put("totalItems", res.getTotalElements());
            map.put("totalPages", res.getTotalPages());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @ApiOperation(value="방송 진행 기록 불러오기", notes="해당 유저의 방송 진행 기록을 반환")
    @GetMapping("/profile/{no}/history/consult")
    public ResponseEntity<Map<String, Object>> getConsultantHistory(@PathVariable int no,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size){
        HashMap<String, Object> map = new HashMap<>();
        List<BroadCastHistoryDto> dtoList = userService.getConsultList(no);
        if(dtoList == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());
            int start = (int)paging.getOffset();
            int end = Math.min(start+paging.getPageSize(), dtoList.size());
            Page<BroadCastHistoryDto> res = new PageImpl<>(dtoList.subList(start, end), paging, dtoList.size());
            map.put("content", res.getContent());
            map.put("currentPage", res.getNumber());
            map.put("totalItems", res.getTotalElements());
            map.put("totalPages", res.getTotalPages());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @ApiOperation(value="상세 히스토리 조회", notes="해당 유저가 참여했던 방송의 상세 기록을 반환")
    @GetMapping("/profile/{user_no}/history/{consulting_no}")
    public ResponseEntity<HistoryDetailDto> getHistoryDetail(@PathVariable int user_no,
                                                             @PathVariable int consulting_no){
        try {
            HistoryUserInfoDto userInfoDto = userService.getHistoryUserInfo(consulting_no);
            HistoryConsultingDto consultingDto = consultingService.getHistoryConsulting(consulting_no);
            HistoryDetailDto dto = new HistoryDetailDto(userInfoDto, consultingDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (IllegalInputException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="작성자의 게시글 목록 불러오기", notes="해당 유저가 작성한 게시글의 목록을 반환")
    @GetMapping("/board/{no}")
    public ResponseEntity<Map<String, Object>> getHistoryDetail(@PathVariable int no,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size){
        HashMap<String, Object> map = new HashMap<>();
        List<BoardGetDto> dtoList = boardService.getBoardWithUserNo(no);

        Pageable paging = PageRequest.of(page, size, Sort.by("dateTime").descending());
        int start = (int)paging.getOffset();
        int end = Math.min(start+paging.getPageSize(), dtoList.size());
        Page<BoardGetDto> res = new PageImpl<>(dtoList.subList(start, end), paging, dtoList.size());
        map.put("content", res.getContent());
        map.put("currentPage", res.getNumber());
        map.put("totalItems", res.getTotalElements());
        map.put("totalPages", res.getTotalPages());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/validation/nickname/{nickname}")
    public ResponseEntity checkNicknameDuplication(@PathVariable("nickname") String nickname) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            userService.getUserByNickname(nickname);
            resultMap.put("msg","이미 사용중인 닉네임입니다.");
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            status = HttpStatus.OK;
            resultMap.put("content","사용 가능한 닉네임 입니다.");
        }finally {
            return new ResponseEntity(resultMap,status);
        }
    }

    @GetMapping("/validation/email/{email}")
    public ResponseEntity checkEmailDuplication(@PathVariable("email") String email) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            userService.getUserByEmail(email);
            resultMap.put("msg","이미 사용중인 이메일입니다.");
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            status = HttpStatus.OK;
            resultMap.put("content","사용 가능한 이메일 입니다.");
        }finally {
            return new ResponseEntity(resultMap,status);
        }
    }
}
