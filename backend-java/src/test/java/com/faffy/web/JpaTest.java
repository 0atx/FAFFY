package com.faffy.web;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.CommentDto;
import com.faffy.web.dto.UserDto;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.FashionCategoryRepository;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.BoardCategory;
import com.faffy.web.jpa.type.Gender;
import com.faffy.web.mapping.PublicUserInfo;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.CommentService;
import com.faffy.web.service.UserCategoryService;
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
    @Autowired
    UserCategoryService userCategoryService;

    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;
    @Test
    @Order(10)
    @DisplayName("add User")
    void save() {
        try {
            System.out.println("유저 생성 -------------");
//            UserDto user = new UserDto();
//            user.setEmail("js727r@ssafy.com");
//            user.setName("이준성1");
//            user.setNickname("jun");
//            user.setGender(Gender.Male);
//            user.setPassword("1234");
//
//            userService.addUser(user);
//
//            user = new UserDto();
//            user.setEmail("mskim@ssafy.com");
//            user.setName("김명석2");
//            user.setNickname("mskim");
//            user.setGender(Gender.Male);
//            user.setPassword("12342");
//            userService.addUser(user);

            UserDto  user = UserDto.builder()
                    .email("js727r@ssafy.com")
                    .name("이준성1")
                    .nickname("jun1")
                    .gender(Gender.Male)
                    .password("1234")
                    .build();
            userService.addUser(user);

            user = UserDto.builder()
                    .email("js727r2@ssafy.com")
                    .name("이준성1")
                    .nickname("jun2")
                    .gender(Gender.Male)
                    .password("1234")
                    .build();
            userService.addUser(user);

            user = UserDto.builder()
                    .email("mskim@ssafy.com")
                    .name("김명석")
                    .nickname("mskim")
                    .gender(Gender.Male)
                    .password("12341112")
                    .build();
            userService.addUser(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        Optional<PublicUserInfo> jun = userService.getUserByNickname("jun");
        if (jun.isPresent())
            System.out.println(jun.get().getName());
        else
            System.out.println("검색 결과없음");
    }


    @Test
    @Order(20)
    @DisplayName("정보 수정2")
    void updateUser2() {
        Optional<PublicUserInfo> jun = userService.getUserByNickname("jun");
        if (jun.isPresent()) {
            PublicUserInfo user = jun.get();
            UserDto userDto = UserDto.builder().no(1).email(user.getEmail()).name("준성").nickname("jun")
                    .gender(user.getGender()).password("444100").info("안녕하세요 이준성입니다.").build();

            System.out.println("-------기존정보 조회 END--------");
            try {
                System.out.println("결과 : " + userService.updateUser(userDto));
            } catch(Exception e) {
                System.out.println("에러 발생 : "+e.getMessage());
            }
        }
    }
    @Test
    @DisplayName("회원 탈퇴")
    @Order(100)
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

    @Test
    @DisplayName("카테고리 추가 - 더미 데이터")
    @Order(45)
    void addCategory() {
        FashionCategory category = FashionCategory.builder().name("hip-hop").build();
        fashionCategoryRepository.save(category);

        category = FashionCategory.builder().name("daily").build();
        fashionCategoryRepository.save(category);

        category = FashionCategory.builder().name("sports").build();
        fashionCategoryRepository.save(category);
    }

    @Test
    @DisplayName("관심 목록 추가")
    @Order(50)
    void addUserCategories() {
        UserDto userDto = UserDto.builder().no(1).build();
        System.out.println("추가결과 : "+userCategoryService.addUserCategory(userDto,"hip-hop"));
        userCategoryService.addUserCategory(userDto,"daily");
        userCategoryService.addUserCategory(userDto,"beach");
    }

    @Test
    @DisplayName("회원 카테고리 삭제")
    @Order(51)
    void deleteCategory() {
        UserDto userDto = UserDto.builder().no(1).build();
        System.out.println("삭제결과 : "+ userCategoryService.deleteUserCategory(userDto,"sports"));

    }

    @Test
    @DisplayName("회원의 카테고리 검색")
    @Order(55)
    void searchCategories() {
        UserDto userDto = UserDto.builder().no(1).build();
        List<FashionCategory> categories = userCategoryService.getUserCategory(userDto);
        for (FashionCategory category : categories
        ) {
            System.out.println("category = " + category.getName());
        }
    }



    @Test
    @DisplayName("게시글 등록")
    @Order(60)
    void writeBoard() {
        UserDto userDto = UserDto.builder().no(1).build();
        BoardDto boardDto = BoardDto.builder().title("첫 글이에요").content("내용입니다").category(BoardCategory.QnA).build();
        System.out.println("결과 : "+boardService.writeBoard(userDto,boardDto));

    }


    @Test
    @DisplayName("게시글 목록 보기")
    @Order(65)
    void showAllBoard() {

    }


    @Test
    @DisplayName("게시글 상세보기")
    @Order(70)
    void boardDetail() {

    }

    @Test
    @DisplayName("댓글 작성")
    @Order(80)
    void writeComments() {
        Optional<User> userByNo = userService.getUserByNo(1);
        Optional<Board> board = boardService.getBoard(1);
        CommentDto commentDto = CommentDto.builder()
                .writer(userByNo.get())
                .board(board.get())
                .content("댓글입니다")
                .build();

        try {
            commentService.addComment(commentDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @DisplayName("게시글 삭제")
    @Order(80)
    void deleteBoard() {

    }


}
