package com.faffy.web.controller;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.BoardGetDto;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.token.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/main")
@CrossOrigin("*")
public class MainController {
    @Autowired
    BoardService boardService;

    private final JwtTokenProvider jwtTokenProvider;

    public static final Logger logger = LoggerFactory.getLogger(BoardController.class);

//    @ApiOperation(value="메인 화면에서 최신순으로 게시물 불러오기", notes="최신순으로 게시물을 (5개) 반환한다.")
//    @PostMapping("/board/date")
//    public ResponseEntity<List<BoardGetDto>> getBoardsByDate(@RequestHeader(value = "X-AUTH-TOKEN") String token) {
////        boardService.getBoardsByDate(PageRequest.of(0, 5));
//
//    }
//
//    @ApiOperation(value="메인 화면에서 조회순으로 게시물 불러오기", notes="조회순으로 게시물을 (5개) 반환한다.")
//    @PostMapping("/board/hit")
//    public ResponseEntity<List<BoardGetDto>> getBoardsByDate(@RequestHeader(value = "X-AUTH-TOKEN") String token) {
////        boardService.getBoardsByHit(PageRequest.of(0, 5));
//        Pageable paging = PageRequest.of(0, 5);
//    }
}
