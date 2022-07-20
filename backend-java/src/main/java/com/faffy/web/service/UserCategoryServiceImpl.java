package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.repository.FashionCategoryRepository;
import com.faffy.web.jpa.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCategoryServiceImpl implements UserCategoryService {
    @Autowired
    UserCategoryRepository userCategoryRepository;
    @Autowired
    FashionCategoryRepository fashionCategoryRepository;
    @Autowired
    UserService userService;

    /**
     * 사용자의 카테고리 목록을 반환
     *
     * @param userDto
     * @return 유저가 설정한 패션 카테고리 리스트
     */
    @Override
    public List<FashionCategory> getUserCategory(UserDto userDto) {
        // 사용자
        Optional<User> user = userService.findUserByNo(userDto.getNo());
        List<FashionCategory> categories = new ArrayList<>();
        if (user.isPresent()) {
            categories = userCategoryRepository.findCategoriesByUser(user.get());
            return categories;
        }
        return categories;
    }

    @Override
    public boolean addUserCategory(UserDto userDto, String categoryName) {
        Optional<User> userResult = userService.findUserByNo(userDto.getNo());
        Optional<FashionCategory> categoryResult = fashionCategoryRepository.findByName(categoryName);
        if (userResult.isPresent() && categoryResult.isPresent()) {
            UserCategory userCategory = UserCategory.builder().user(userResult.get()).category(categoryResult.get()).build();
            userCategoryRepository.save(userCategory);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUserCategory(UserDto userDto, String categoryName) {
        Optional<FashionCategory> categoryResult = fashionCategoryRepository.findByName(categoryName);
        Optional<User> userResult = userService.findUserByNo(userDto.getNo());
        if (userResult.isPresent() && categoryResult.isPresent()) {
            UserCategory userCategory = UserCategory.builder().user(userResult.get()).category(categoryResult.get()).build();
            userCategoryRepository.delete(userCategory);
            return true;
        }
        return false;
    }


}
