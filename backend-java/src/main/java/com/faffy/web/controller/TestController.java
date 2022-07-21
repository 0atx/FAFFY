package com.faffy.web.controller;

import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.UserRepository;
import com.faffy.web.jpa.type.Gender;
import com.faffy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }


}
