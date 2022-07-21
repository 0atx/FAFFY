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
    public List<FashionCategory> getUserCategory(int user_no) {
        // 사용자
//        Optional<User> user = userService.getUserByNo(userDto.getNo());
        List<FashionCategory> categories = new ArrayList<>();
        try {
            User user = userService.getUserByNo(user_no);

            categories = userCategoryRepository.findCategoriesByUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return categories;
        }
    }

    @Override
    public boolean addUserCategory(int user_no, String categoryName) {
        try {
            User user = userService.getUserByNo(user_no);
            Optional<FashionCategory> categoryResult = fashionCategoryRepository.findByName(categoryName);
            if ( categoryResult.isPresent()) {
                UserCategory userCategory = UserCategory.builder().user(user).category(categoryResult.get()).build();
                userCategoryRepository.save(userCategory);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return false;
        }
    }

    @Override
    public boolean deleteUserCategory(int user_no, String categoryName) {
        Optional<FashionCategory> categoryResult = fashionCategoryRepository.findByName(categoryName);
        try {
            User userResult = userService.getUserByNo(user_no);
            if (categoryResult.isPresent()){
                UserCategory userCategory = UserCategory.builder().user(userResult).category(categoryResult.get()).build();
                userCategoryRepository.delete(userCategory);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            return false;
        }
    }


}
