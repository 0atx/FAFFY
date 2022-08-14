package com.faffy.web.service;

import com.faffy.web.dto.*;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.UserNoAndNicknameMask;

import java.io.File;
import java.time.LocalDate;
import java.util.List;


public interface UserService {
    /**
     * no값에 해당하는 유저 정보를 조회합니다.
     *
     * @param no
     * @return 모든 정보가 담긴 User 객체, 해당 값 없을 시 null
     * @throws Exception no에 해당하는 유저가 없을 시 예외 발생
     */
    public User getUserByNo(int no) throws Exception;

    /**
     * 닉네임으로 유저 정보를 조회합니다.
     * @param nickname 검색하려는 유저의 닉네임
     * @return 해당 유저
     * @throws Exception 해당하는 유저가 없을 시 예외 발생
     */
    public User getUserByNickname(String nickname) throws Exception;

    /**
     * 이메일로 유저 정보를 조회합니다.
     * @param email 검색하려는 유저의 이메일
     * @return 해당 유저
     * @throws Exception 해당하는 유저가 없을 시 예외 발생
     */
    public User getUserByEmail(String email) throws Exception;
    public List<UserNoAndNicknameMask> findAllUsers();


    /**
     * no에 해당하는 유저 정보를 삭제합니다.
     * @param no 삭제할 유저 번호
     * @throws Exception 해당하는 유저가 없을 시 예외 발생
     */
    public void deleteUser(int no) throws Exception;

    /**
     * 이메일, 패스워드로 유저 객체를 조회합니다.
     *
     * @param userDto
     * @return 로그인한 유저의 공개 정보 객체 반환
     * @throws Exception 이메일, 패스워드 오류 시 예외 발생
     */
    public UserPublicDto login(UserLoginDto userDto) throws Exception;

    /**
     * 새로운 유저를 DB에 등록합니다.
     *
     * @param userDto
     * @return 추가된 User 객체 반환
     * @throws DataIntegrityException 이메일 중복, 닉네임 중복시 발생
     */
    public User addUser(UserDto userDto) throws IllegalInputException, DataIntegrityException;

    /**
     * 회원 정보를 수정합니다.
     * @param userDto user_no가 포함된 UserDto
     * @throws IllegalInputException 일부 값이 유효하지 않을 경우(null or 빈 값)
     * @throws DataNotFoundException user_no로 회원 정보를 찾을 수 없는 경우
     * @throws Exception DataIntegrity를 위배한 경우(Unique 위배)
     */
    public User updateUser(UserUpdateDto userDto) throws DataNotFoundException, IllegalInputException ,Exception;

//    /**
//     * 파일 업로드 되는지 확인용 서비스
//     * @param file
//     * @return
//     * @throws DataNotFoundException
//     * @throws IllegalInputException
//     * @throws Exception
//     */
//    public User updateUserImg(MultipartFile file) throws DataNotFoundException, IllegalInputException ,Exception;

    void logout(String token);

    UserGetDetailDto getProfile(int no);

    File getProfileImg(int no) throws IllegalInputException;

    List<BroadCastHistoryDto> getPartiList(int no);

    List<BroadCastHistoryDto> getConsultList(int no);

    HistoryUserInfoDto getHistoryUserInfo(int no) throws IllegalInputException;
    void deleteUserImg(int no) throws DataNotFoundException;

    boolean setUserPwdByUserEmail(String email, String pwd);

    User getUserByEmailBirthday(String email, LocalDate birthday);

    List<UserPublicDto> searchByKeyword(String keyword) throws Exception;
}
