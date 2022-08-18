package com.faffy.web.controller;

import com.faffy.web.dto.CommentAddDto;
import com.faffy.web.dto.CommentUpdateDto;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.CommentService;
import com.faffy.web.service.CommentServiceImpl;
import com.faffy.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin("*")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    UserService userService;

    @Autowired
    BoardService boardService;

    public static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("")
    public ResponseEntity addComment(@RequestBody CommentAddDto commentAddDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            commentService.addComment(commentAddDto);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(resultMap,status);
    }

    @PutMapping("")
    public ResponseEntity updateComment(@RequestBody CommentUpdateDto commentUpdateDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            commentService.updateComment(commentUpdateDto);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(resultMap,status);
    }

    @DeleteMapping("/{comment_no}")
    public ResponseEntity deleteComment(@PathVariable("comment_no") int comment_no) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            commentService.deleteComment(comment_no);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(resultMap,status);
    }
}
