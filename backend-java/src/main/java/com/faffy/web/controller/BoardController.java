package com.faffy.web.controller;

import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    BoardService boardService;
    
    @GetMapping("")
    ResponseEntity getAllBoards() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        List<Board> allBoard = boardService.getAllBoard();
        System.out.println("게시판 목록---------");
        for (Board board :
                allBoard) {
            System.out.println(board.getTitle());
        }
        System.out.println("게시판 목록---------END");
//        try {
//            List<Board> allBoard = boardService.getAllBoard();
//            for (Board b:
//                 allBoard) {
//                resultMap.put("content", b.getTitle());
//
//            }
//        } catch (Exception e) {
//            logger.error("게시글 목록 불러오기 실패 {}",e.toString());
//            resultMap.put("msg",e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
        return new ResponseEntity(resultMap,status);
    }


}
