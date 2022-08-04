package com.faffy.web;

import com.faffy.web.dto.*;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.FashionCategoryRepository;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.BoardCategory;
import com.faffy.web.jpa.type.Gender;
import com.faffy.web.jpa.type.LoginType;
import com.faffy.web.jpa.type.PublicUserInfo;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.CommentService;
import com.faffy.web.service.UserCategoryService;
import com.faffy.web.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @DisplayName("유저 추가")
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

            UserDto user = UserDto.builder()
                    .email("js727r@ssafy.com")
                    .name("이준성1")
                    .nickname("jun1")
                    .gender(Gender.Male)
                    .password("1234")
                    .birthday("1996-07-27")
                    .info("이준성입니다")
                    .loginType(LoginType.valueOf("SITE"))
                    .build();
            userService.addUser(user);

            user = UserDto.builder()
                    .email("js727r2@ssafy.com")
                    .name("이준성2")
                    .nickname("jun2")
                    .gender(Gender.Male)
                    .password("1234")
                    .birthday("1995-01-01")
                    .loginType(LoginType.valueOf("SITE"))
                    .build();
            userService.addUser(user);

            user = UserDto.builder()
                    .email("mskim@ssafy.com")
                    .name("김명석")
                    .nickname("mskim")
                    .gender(Gender.Male)
                    .password("12341112")
                    .birthday("1997-01-01")
                    .loginType(LoginType.valueOf("SITE"))
                    .build();
            userService.addUser(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("유저 생성 ------------- END");


    }

    @Test
    @Order(11)
    @DisplayName("유저 목록 보기")
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
        User jun = null;
        try {
            jun = userService.getUserByNickname("jun1");
            System.out.println(jun.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @Test
    @Order(20)
    @DisplayName("정보 수정")
    void updateUser2() {
        try {
            // 정상 수정
            User user = userService.getUserByNickname("jun1");
            UserDto userDto = UserDto.builder().email(user.getEmail()).name("준성").nickname("jun")
                    .gender(user.getGender()).password("444100").info("안녕하세요 이준성입니다.").build();

            userService.updateUser(userDto);
            System.out.println("수정완료");
            // 이상한 값 수정

            // 중복 값 수정
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @Order(21)
    @DisplayName("이상한 값으로 수정")
    void updateUser3() {
        try {
            User user = userService.getUserByNickname("jun");
            UserDto userDto = UserDto.builder().no(1).email(user.getEmail()).name("").nickname("jun")
                    .gender(user.getGender()).password("444100").info("안녕하세요 이준성입니다.").build();

            userService.updateUser(userDto);
            System.out.println("수정완료");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @Order(22)
    @DisplayName("중복 값으로 수정")
    void updateUser4() {
        try {
            // 정상 수정
            User user = userService.getUserByNickname("jun");
            UserDto userDto = UserDto.builder().no(1).email(user.getEmail()).name("준성").nickname("jun2")
                    .gender(user.getGender()).password("444100").info("안녕하세요 이준성입니다.").build();
            userService.updateUser(userDto);
            System.out.println("수정완료");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("회원 탈퇴")
    @Order(100)
    void deleteUser() {
        try {
            userService.deleteUser(2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("로그인")
    @Order(40)
    void loginUser() {
        try {
            UserLoginDto userDto = UserLoginDto.builder().email("js727r@ssafy.com").password("44332").build();
            UserPublicDto loginedUser = userService.login(userDto);
            System.out.println(loginedUser.getNickname());
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

//    @Test
//    @DisplayName("관심 목록 추가")
//    @Order(50)
//    void addUserCategories() {
//        System.out.println("추가결과 : " + userCategoryService.addUserCategory(1, "hip-hop"));
//        userCategoryService.addUserCategory(1, "daily");
//        userCategoryService.addUserCategory(1, "beach");
//    }

//    @Test
//    @DisplayName("회원 카테고리 삭제")
//    @Order(51)
//    void deleteCategory() {
//        System.out.println("삭제결과 : " + userCategoryService.deleteUserCategory(1, "sports"));
//
//    }

    @Test
    @DisplayName("회원의 카테고리 검색")
    @Order(55)
    void searchCategories() {
        List<FashionCategory> categories = userCategoryService.getUserCategory(1);
        for (FashionCategory category : categories
        ) {
            System.out.println("category = " + category.getName());
        }
    }


    @Test
    @DisplayName("게시글 등록")
    @Order(60)
    void writeBoard() {
        try {
            BoardDto boardDto = BoardDto.builder().title("첫 글이에요").content("내용입니다").category(BoardCategory.QnA).build();
            boardService.writeBoard( boardDto,1);

            boardDto = BoardDto.builder().title("두 번째 글").content("내용2").category(BoardCategory.Free).build();
            boardService.writeBoard(boardDto,2);

            boardDto = BoardDto.builder().title("세 번째 글").content("내용3").category(BoardCategory.Free).build();
            boardService.writeBoard(boardDto,3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("게시글 수정")
    @Order(61)
    void updateBoard() {
        int user_no=2;
        try {
            BoardUpdateDto boardUpdateDto = BoardUpdateDto.builder()
                    .no(1)
                    .title("수정제목")
                    .content("수정내용")
                    .category(BoardCategory.Free)
                    .build();
            boardService.updateBoard(boardUpdateDto,user_no);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("게시글 목록 보기")
    @Order(65)
    void showAllBoard() {
        Map<String,Object> resultMap = new HashMap<>();
        List<Board> allBoard = boardService.getAllBoard();
        resultMap.put("content",allBoard);
        System.out.println(resultMap);
        System.out.println("게시판 목록---------");
        for (Board board :
                allBoard) {
            System.out.println(board.getTitle());
        }
        System.out.println("게시판 목록---------END");

    }


    @Test
    @DisplayName("게시글 상세보기")
    @Order(70)
    void boardDetail() {
        Board board = null;
        try {
            board = boardService.getBoard(1);
            System.out.println("제목 : " +
                    board.getTitle());
            System.out.println("내용 : " +
                    board.getContent());
            System.out.println("작성자 : " +
                    board.getUser().getNickname());
            System.out.println("작성일시 : " +
                    board.getDatetime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    @Test
    @DisplayName("댓글 작성")
    @Order(80)
    void writeComments() {
        try {
            CommentAddDto commentAddDto = CommentAddDto.builder()
                    .board_no(1)
                    .writer_no(1)
                    .content("댓글입니다.")
                    .build();
            commentService.addComment(commentAddDto);

            commentAddDto = CommentAddDto.builder()
                    .board_no(1)
                    .writer_no(2)
                    .content("댓글입니다.2")
                    .build();
            commentService.addComment(commentAddDto);

            commentAddDto = CommentAddDto.builder()
                    .board_no(1)
                    .writer_no(1)
                    .content("댓글입니다.3")
                    .build();
            commentService.addComment(commentAddDto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("댓글 수정")
    @Order(81)
    void updateComment() {
        CommentUpdateDto commentUpdateDto = null;
        try {
            commentUpdateDto = CommentUpdateDto.builder()
                    .comment_no(3)
                    .content("수정된 내용입니다.")
                    .build();
            commentService.updateComment(commentUpdateDto);
        } catch (IllegalInputException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("댓글 삭제")
    @Order(82)
    void deleteComment() {
        try {
            commentService.deleteComment(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("게시글 댓글 목록 보기")
    @Order(83)
    @Transactional
    void getBoardComments() {
        List<Comment> boardComments = commentService.getBoardComments(1);
        for (Comment comment :
                boardComments) {
            System.out.printf("게시글 : %s 작성자 : %s 내용 : %s\n" ,comment.getBoard().getTitle(), comment.getWriter().getNickname(),comment.getContent());
        }

    }

    @Test
    @DisplayName("게시글 삭제")
    @Order(85)
    void deleteBoard() {
        int user_no=2;
        try {
            boardService.deleteBoard(2,user_no);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("팔로우")
    @Order(110)
    void follow() {

    }

    @Test
    @DisplayName("팔로우 취소")
    @Order(120)
    void followCancle() {

    }

    @Test
    @DisplayName("팔로우 목록 보기")
    @Order(130)
    void followList() {

    }

    @Test
    @DisplayName("팔로잉 목록 보기")
    @Order(140)
    void followedList() {

    }

}
