package com.faffy.web;

import com.faffy.web.dto.UserDto;
import com.faffy.web.exception.DataIntegrityException;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.FashionCategoryRepository;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.Gender;
import com.faffy.web.mapping.PublicUserInfo;
import com.faffy.web.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JpaTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    FashionCategoryRepository fashionCategoryRepository;

    @Autowired
    UserService userService;


    @Test
    @Order(10)
    @DisplayName("add User")
    void save() {
        try {
            System.out.println("유저 생성 -------------");
            UserDto user = new UserDto();
            user.setEmail("js727r@ssafy.com");
            user.setName("이준성");
            user.setNickname("jun");
            user.setGender(Gender.Male);
            user.setPassword("1234");

            userService.addUser(user);

            user = new UserDto();
            user.setEmail("mskim@ssafy.com");
            user.setName("김명석");
            user.setNickname("mskim");
            user.setGender(Gender.Male);
            user.setPassword("12342");
            userService.addUser(user);

            user = new UserDto();
            user.setEmail("js727r@ssafy.com");
            user.setName("이준성");
            user.setNickname("jun");
            user.setGender(Gender.Male);
            user.setPassword("1234");
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("유저 생성 ------------- END");


    }

    @Test
    @Order(11)
    @DisplayName("findAll")
    void findAllUsers() {
        System.out.println("유저 검색 ------------- ");

        List<PublicUserInfo> allUsers = userService.findAllUsers();
        System.out.println("userCount : " +
                allUsers.size());
        for (PublicUserInfo user :
                allUsers) {
            System.out.println("user = " + user.getNickname());
        }
        System.out.println("유저 검색 ------------- END");
    }

    @Test
    @Order(12)
    @DisplayName("닉네임 검색")
    void nicknameSearch() {
        Optional<PublicUserInfo> jun = userService.findUserByNickname("jun");
        if (jun.isPresent())
            System.out.println(jun.get().getName());
        else
            System.out.println("검색 결과없음");
    }

    @Test
    @Order(20)
    @DisplayName("정보 수정")
    void updateUser() {
        Optional<PublicUserInfo> jun = userService.findUserByNickname("jun");
        if (jun.isPresent()) {
            PublicUserInfo user = jun.get();
            UserDto userDto = UserDto.builder().no(user.getNo()).email(user.getEmail()).name("준성").nickname(user.getNickname())
                    .gender(user.getGender()).password("444100").info("안녕하세요 이준성입니다.").build();

            try {
                Optional<User> result = userService.updateUser(userDto);
                if (result.isPresent()) {
                    User updatedUser = result.get();
                    System.out.println(updatedUser.toString());
                }
            } catch (IllegalInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("회원 탈퇴")
    @Order(30)
    void deleteUser() {
        try {
            userService.deleteUser(3);
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("로그인")
    @Order(40)
    void loginUser() {
        UserDto userDto = UserDto.builder().email("js727r@ssafy.com").password("444100").build();
       
        Optional<PublicUserInfo> loginedUser = userService.login(userDto);
        if (loginedUser.isPresent()) {
            System.out.println(loginedUser.get().getNickname());
        } else {
            System.out.println("로그인 실패");
        }
    }
//    @Test
//    @Order(20)
//    void addCategories() {
//        System.out.println("카테고리 생성---------");
//        FashionCategory category = new FashionCategory();
//        category.setName("hip-hop");
//        fashionCategoryRepository.save(category);
//
//        category = new FashionCategory();
//        category.setName("daily");
//        fashionCategoryRepository.save(category);
//        System.out.println("카테고리 생성--------- END");
//
//    }
//
//    @Test
//    @Transactional
//    @Order(21)
//    void addUserCategory() {
//        System.out.println("유저카테고리 추가 -------------");
//        UserDto userDto = new UserDto();
//        userDto.setNickname("jun");
//
//        FashionCategory category = fashionCategoryRepository.findFashionCategoryByName("hip-hop");
//
//        System.out.println("유저카테고리 추가 -------------END");
//
//    }
//
//    @Test
//    @Transactional
//    @Order(30)
//    void select() {
//
//    }
//
//    @Test
//    @Transactional
//    @Order(40)
//    void findAllUsers() {
//        List<User> users = userRepository.findAll();
//        System.out.println("모든 유저 목록 조회 ----------");
//        for (User user :
//                users) {
//            System.out.println("user.getNickname() = " + user.getNickname());
//        }
//        System.out.println("모든 유저 목록 조회 END----------");
//
//    }
}
