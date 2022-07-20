package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.mapping.PublicUserInfo;

import java.util.List;
import java.util.Optional;


public interface UserService {
    /**
     * no값에 해당하는 유저 정보를 조회합니다.
     * @param no
     * @return 모든 정보가 담긴 User 객체, 해당 값 없을 시 null
     */
    public Optional<User> getUserByNo(int no);

    /**
     * 닉네임으로 유저 정보를 조회합니다.
     * @param nickname
     * @return 해당 유저의 공개 정보, 없다면 null 반환
     */
    public Optional<PublicUserInfo> getUserByNickname(String nickname);

    /**
     * 이메일로 유저 정보를 조회합니다.
     * @param email
     * @return 해당 유저의 공개 정보, 없다면 null 반환
     */
    public Optional<PublicUserInfo> getUserByEmail(String email);
    public List<PublicUserInfo> findAllUsers();


    public void deleteUser(int no) throws DataNotFoundException;

    /**
     * 아이디, 패스워드로 유저 객체를 조회합니다.
     * @param userDto
     * @return 로그인한 유저의 공개 정보 객체 반환
     */
    public Optional<PublicUserInfo> login(UserDto userDto);

    /**
     * 새로운 유저를 DB에 등록합니다.
     *
     * @param userDto
     * @return 추가된 User 객체 반환
     * @throws DataIntegrityException 이메일 중복, 닉네임 중복시 발생
     */
    public User addUser(UserDto userDto) throws DataIntegrityException;

    /**
     * user_no가 포함된 UserDto로 회원 정보를 수정
     * @param userDto
     * @return 수정된 User 객체
     * @throws IllegalInputException 수정하려는 일부 값이 유효하지 않을 경우
     * @throws DataNotFoundException user_no로 회원 정보를 찾을 수 없는 경우
     */
    public int updateUser(UserDto userDto) throws DataNotFoundException, IllegalInputException;
}
