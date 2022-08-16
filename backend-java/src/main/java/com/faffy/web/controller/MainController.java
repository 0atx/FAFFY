package com.faffy.web.controller;

import com.faffy.web.dto.*;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.ConsultingService;
import com.faffy.web.service.UserService;
import com.faffy.web.service.token.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    UserService userService;
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

    @ApiOperation(value="메인 화면에서 파일이 첨부된 게시물을 최신순으로 불러오기", notes="첨부 파일이 있는 게시물 최신순으로 (4개) 반환")
    @GetMapping("/board/image")
    public ResponseEntity<List<BoardGetDto>> getBoardsImageNoByDate() {
        List<BoardGetDto> res = boardService.getBoardsWithImageByDate(PageRequest.of(0, 4));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="메인 화면에서 진행 중인 방송 불러오기", notes="참여자 수가 높은 순으로 방송정보를 반환한다.")
    @GetMapping("/consulting/hit")
    public ResponseEntity getConsultingsByViewCount(@RequestParam int size) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ConsultingGetDto> res = consultingService.getConsultingsByViewCount(PageRequest.of(0, size));
        resultMap.put("content", res);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value="메인 화면에서 검색 결과 반환", notes="해당 키워드의 유저, 방송, 게시물 목록을 반환한다.")
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getParticipantHistory(@RequestParam String keyword,
                                                                     @RequestParam(defaultValue = "0") int userPage,
                                                                     @RequestParam(defaultValue = "10") int userSize,
                                                                     @RequestParam(defaultValue = "0") int boardPage,
                                                                     @RequestParam(defaultValue = "10") int boardSize,
                                                                     @RequestParam(defaultValue = "0") int consultingPage,
                                                                     @RequestParam(defaultValue = "10") int consultingSize){
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<UserGetDetailDto> userDtoList = userService.searchByKeyword(keyword);
            List<BoardGetDto> boardDtoList = boardService.searchByKeyword(keyword);
            List<ConsultingGetDto> consultingDtoList = consultingService.searchByKeyword(keyword);

            Pageable paging = PageRequest.of(userPage, userSize);
            int start = (int) paging.getOffset();
            int end = Math.min(start + paging.getPageSize(), userDtoList.size());
            Page<UserGetDetailDto> userRes = new PageImpl<>(userDtoList.subList(start, end), paging, userDtoList.size());
            HashMap<String, Object> tmpMap = new HashMap<>();
            tmpMap.put("content", userRes.getContent());
            tmpMap.put("currentPage", userRes.getNumber());
            tmpMap.put("totalItems", userRes.getTotalElements());
            tmpMap.put("totalPages", userRes.getTotalPages());
            map.put("user", tmpMap);

            paging = PageRequest.of(boardPage, boardSize);
            start = (int) paging.getOffset();
            end = Math.min(start + paging.getPageSize(), boardDtoList.size());
            Page<BoardGetDto> boardRes = new PageImpl<>(boardDtoList.subList(start, end), paging, boardDtoList.size());
            tmpMap = new HashMap<>();
            tmpMap.put("content", boardRes.getContent());
            tmpMap.put("currentPage", boardRes.getNumber());
            tmpMap.put("totalItems", boardRes.getTotalElements());
            tmpMap.put("totalPages", boardRes.getTotalPages());
            map.put("board", tmpMap);

            paging = PageRequest.of(consultingPage, consultingSize);
            start = (int) paging.getOffset();
            end = Math.min(start + paging.getPageSize(), consultingDtoList.size());
            Page<ConsultingGetDto> consultingRes = new PageImpl<>(consultingDtoList.subList(start, end), paging, consultingDtoList.size());
            tmpMap = new HashMap<>();
            tmpMap.put("content", consultingRes.getContent());
            tmpMap.put("currentPage", consultingRes.getNumber());
            tmpMap.put("totalItems", consultingRes.getTotalElements());
            tmpMap.put("totalPages", consultingRes.getTotalPages());
            map.put("consulting", tmpMap);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("검색 과정에서 에러 발생");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
