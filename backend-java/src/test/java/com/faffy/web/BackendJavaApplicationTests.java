package com.faffy.web;

import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.Gender;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest

@EnableJpaRepositories(basePackages = {"com.faffy.web"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class BackendJavaApplicationTests {

//    @Test
//    void contextLoads() {
//    }

    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
    void save() {
        User user = new User();
        user.setEmail("js727r@ssafy.com");
        user.setName("이준성");
        user.setNickname("jun");
        user.setGender(Gender.Male);
        user.setPassword("1234");
        userRepository.save(user);
    }

    @Test
    @Order(2)
    void select() {
        User jun = userRepository.findByNickname("jun");
        System.out.println("jun = " + jun);
    }
}
