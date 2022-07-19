package com.faffy.web.controller;

import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @GetMapping("/save")
    public String save() {
        User user = new User();
        user.setEmail("js727r@ssafy.com");
        user.setName("이준성");
        user.setNickname("jun");
        user.setGender(Gender.Male);
        user.setPassword("1234");

        userRepository.save(user);

        return "success";
    }

    @GetMapping("/find/{nickname}")
    public String find(@PathVariable("nickname") String nickname) {
        User user = userRepository.findByNickname(nickname);
        if (user == null)
            return nickname + " hasn't been found";
        else
        return user.getName();

    }
}
