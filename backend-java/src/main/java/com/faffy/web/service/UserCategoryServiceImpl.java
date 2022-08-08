package com.faffy.web.service;

import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.ExceptionMsg;
import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.entity.UserCategory;
import com.faffy.web.jpa.repository.FashionCategoryRepository;
import com.faffy.web.jpa.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * @param user_no
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
    public UserCategory addUserCategory(int user_no, String categoryName) throws Exception {
        try {
            User user = userService.getUserByNo(user_no);
            FashionCategory fashionCategory = fashionCategoryRepository.findByName(categoryName).orElseThrow(() -> new IllegalArgumentException(ExceptionMsg.CATEGORY_NOT_FOUND_MSG));

            UserCategory userCategory = UserCategory.builder().user(user).category(fashionCategory).build();
            userCategoryRepository.save(userCategory);
            return userCategory;
        } catch (Exception e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    @Override
    public String deleteUserCategory(int user_no, String categoryName) throws Exception {
        try {
            FashionCategory fashionCategory = fashionCategoryRepository.findByName(categoryName).orElseThrow(() -> new IllegalArgumentException(ExceptionMsg.CATEGORY_NOT_FOUND_MSG));
            User userResult = userService.getUserByNo(user_no);
            UserCategory userCategory = UserCategory.builder().user(userResult).category(fashionCategory).build();
            userCategoryRepository.delete(userCategory);
            return categoryName;
        } catch (Exception e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<String> setUserCategories(User user, List<String> categoryNames) throws Exception {
        List<String> result = new ArrayList<>();

        try {
            List<UserCategory> userCategories = userCategoryRepository.findByUser(user);
            for(UserCategory category : userCategories) {
                userCategoryRepository.delete(category);
            }
            for (String categoryName : categoryNames) {
                addUserCategory(user.getNo(),categoryName);
            }


        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new DataNotFoundException("여기서 에러 발생했어요");
        }

        return result;
    }


}
