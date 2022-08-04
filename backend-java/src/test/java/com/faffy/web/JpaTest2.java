package com.faffy.web;

import com.faffy.web.dto.UserDto;
import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.entity.ConsultingLog;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.ConsultingLogRepository;
import com.faffy.web.jpa.repository.ConsultingRepository;
import com.faffy.web.jpa.repository.FashionCategoryRepository;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.Gender;
import com.faffy.web.jpa.type.PublicUserInfo;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.CommentService;
import com.faffy.web.service.UserCategoryService;
import com.faffy.web.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JpaTest2 {


    @Autowired
    UserRepository userRepository;

    @Autowired
    FashionCategoryRepository fashionCategoryRepository;

    @Autowired
    ConsultingRepository consultingRepository;

    @Autowired
    ConsultingLogRepository consultingLogRepository;

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
            UserDto user = UserDto.builder()
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




    }

    @Test
    @Order(11)
    @DisplayName("findAll")
    void findAllUsers() {

        List<PublicUserInfo> allUsers = userService.findAllUsers();
        System.out.println("userCount : " +
                allUsers.size());
        for (PublicUserInfo user :
                allUsers) {
            System.out.println("user = " + user.getNickname());
        }
    }

    @Test
    @Order(121)
    @DisplayName("유저번호 검색")
    void noSearch() {
        try {
            User user = userService.getUserByNo(4);
            System.out.println("검색결과 : " +
                    user.getNickname());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @Order(20)
    @DisplayName("정보 수정2")
    void updateUser2() {
        try {
            User user = userService.getUserByNickname("jun1");
            UserDto userDto = UserDto.builder().no(1).email(user.getEmail()).name("준성").nickname("jun")
                    .gender(user.getGender()).password("444100").info("안녕하세요 이준성입니다.").build();

            userService.updateUser(userDto);
            System.out.println("수정완료");

        } catch (Exception e) {
            System.out.println( e.getMessage());
        }

    }

    @Test
    @DisplayName("방송 더미 데이터 삽입")
    void insertConsultings(){
        //유저 1, 유저 2로 진행한 방송 12개씩 DB에 삽입
        try{
            User user1 = userRepository.findByNo(1).orElse(null);
            User user2 = userRepository.findByNo(2).orElse(null);
            Consulting consulting = null;
            for(int i=0; i<24; i++){
                if(i % 2 == 0) {
                    consulting = Consulting.builder()
                            .startTime(LocalDateTime.now())
                            .endTime(LocalDateTime.now())
                            .title("모델 같이 입는 방법 전수("+i+")")
                            .intro("야! 너두 모델같이 입을 수 있어!("+i+")")
                            .roomSize(10)
                            .viewCount(100)
                            .consultant(user1)
                            .build();
                    consultingRepository.save(consulting);
                    ConsultingLog log = new ConsultingLog(consulting, user1);
                    consultingLogRepository.save(log);
                    log = new ConsultingLog(consulting, user2);
                    consultingLogRepository.save(log);
                }
                else{
                    consulting = Consulting.builder()
                            .startTime(LocalDateTime.now())
                            .endTime(LocalDateTime.now())
                            .title("모델 같이 입는 방법 전수("+i+")")
                            .intro("야! 너두 모델같이 입을 수 있어!("+i+")")
                            .roomSize(10)
                            .viewCount(100)
                            .consultant(user2)
                            .build();
                    consultingRepository.save(consulting);
                    ConsultingLog log = new ConsultingLog(consulting, user1);
                    consultingLogRepository.save(log);
                    log = new ConsultingLog(consulting, user2);
                    consultingLogRepository.save(log);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
