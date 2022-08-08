package com.faffy.web.controller;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.BoardGetDto;
import com.faffy.web.dto.ConsultingGetDto;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.ConsultingService;
import com.faffy.web.service.token.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    ConsultingService consultingService;

    private final JwtTokenProvider jwtTokenProvider;

    public static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @ApiOperation(value="메인 화면에서 최신순으로 게시물 불러오기", notes="최신순으로 게시물을 (5개) 반환한다.")
    @GetMapping("/board/date")
    public ResponseEntity<List<BoardGetDto>> getBoardsByDate() {
        List<BoardGetDto> res = boardService.getBoardsByDate(PageRequest.of(0, 5));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="메인 화면에서 조회순으로 게시물 불러오기", notes="조회순으로 게시물을 (5개) 반환한다.")
    @GetMapping("/board/hit")
    public ResponseEntity<List<BoardGetDto>> getBoardsByHit() {
        List<BoardGetDto> res = boardService.getBoardsByHit(PageRequest.of(0, 5));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="메인 화면에서 최신순으로 게시물 이미지 불러오기", notes="최신순으로 게시물의 첨부 이미지를 (4개) 반환한다.")
    @GetMapping("/board/image")
    public ResponseEntity<List<Integer>> getBoardsImageNoByDate() {
        List<Integer> res = boardService.getBoardsImageNoByDate(PageRequest.of(0, 4));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="메인 화면에서 진행 중인 방송 불러오기", notes="참여자 수가 높은 순으로 방송정보를 (4개) 반환한다.")
    @GetMapping("/consulting/hit")
    public ResponseEntity<List<ConsultingGetDto>> getConsultingsByViewCount() {
        List<ConsultingGetDto> res = consultingService.getConsultingsByViewCount(PageRequest.of(0, 4));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
