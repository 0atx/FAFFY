package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.entity.UserCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserCategoryService {
    public List<FashionCategory> getUserCategory(UserDto userDto);

    /**
     * user_no에 해당하는 유저의 관심분야을 추가합니다.
     * @param userDto user_no가 설정된 UserDto 객체
     * @param categoryName 추가할 카테고리 이름
     * @return 정상 추가시 true, 유저 정보나 카테고리를 찾을 수 없을 경우 false
     */
    public boolean addUserCategory(UserDto userDto, String categoryName);

    /**
     * user_no에 해당하는 유저의 관심분야를 삭제합니다.
     * @param userDto user_no가 설정된 UserDto 객체
     * @param categoryName 삭제할 카테고리 이름
     * @return 정상 삭제가 일어나거나 아무런 삭제도 일어나지 않을 경우 true, 유저 정보를 찾을 수 없거나 카테고리명이 잘못된 경우 false
     */
    public boolean deleteUserCategory(UserDto userDto, String categoryName);

}
