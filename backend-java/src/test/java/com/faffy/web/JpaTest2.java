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
        try{
            User user1 = userRepository.findByNo(1).orElse(null);
            User user2 = userRepository.findByNo(2).orElse(null);

            Consulting consulting = Consulting.builder()
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now())
                    .title("전직 모델의 날씬해 보이게 입는 법 컨설팅")
                    .intro("나에게 잘 맞는 옷을 골라야 합니다!")
                    .roomSize(10)
                    .viewCount(100)
                    .consultant(user1)
                    .build();
            consultingRepository.save(consulting);
            ConsultingLog log = new ConsultingLog(consulting, user1);
            consultingLogRepository.save(log);

            Thread.sleep(1000);
            consulting = Consulting.builder()
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now())
                    .title("스트릿 패션의 정수를 보여주마")
                    .intro("홍대 길바닥 패션 경력 10년 짬바가 옷 입는법 알려드림")
                    .roomSize(12)
                    .viewCount(120)
                    .consultant(user2)
                    .build();
            consultingRepository.save(consulting);
            log = new ConsultingLog(consulting, user2);
            consultingLogRepository.save(log);

            Thread.sleep(1000);
            consulting = Consulting.builder()
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now())
                    .title("격식 있는 듯 없는 듯 입는 패션 전수")
                    .intro("적당히 포멀하면서 적당히 캐주얼한 옷 입기")
                    .roomSize(8)
                    .viewCount(80)
                    .consultant(user1)
                    .build();
            consultingRepository.save(consulting);
            log = new ConsultingLog(consulting, user1);
            consultingLogRepository.save(log);
            log = new ConsultingLog(consulting, user2);
            consultingLogRepository.save(log);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
