package com.faffy.web.controller;

import com.faffy.web.dto.FollowRequestDto;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.service.FollowService;
import com.faffy.web.service.FollowServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin("*")
public class FollowController {

    @Autowired
    FollowServiceImpl followService;

    public static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/following/{no}")
    public ResponseEntity getFolling(@PathVariable("no") int no) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<UserPublicDto> following = followService.getFollowing(no);
            resultMap.put("content",following);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(resultMap, status);

    }
    @GetMapping("/follower/{no}")
    public ResponseEntity getFollower(@PathVariable("no") int no) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<UserPublicDto> follower = followService.getUserFollower(no);
            resultMap.put("content",follower);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(resultMap, status);

    }
    @PostMapping("")
    public ResponseEntity follow(@RequestBody FollowRequestDto followRequestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            String follow = followService.follow(followRequestDto.getFrom(), followRequestDto.getTo());
            resultMap.put("content",follow);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }


        return new ResponseEntity(resultMap, status);

    }
    @DeleteMapping("")
    public ResponseEntity followCancel(@RequestBody FollowRequestDto followRequestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            String follow = followService.followCancel(followRequestDto.getFrom(), followRequestDto.getTo());
            resultMap.put("content",follow);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }


        return new ResponseEntity(resultMap, status);

    }
}
