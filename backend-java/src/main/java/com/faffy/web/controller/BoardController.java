package com.faffy.web.controller;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.BoardGetDto;
import com.faffy.web.dto.BoardUpdateDto;
import com.faffy.web.dto.CommentGetDto;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.CommentService;
import com.faffy.web.service.token.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
@CrossOrigin("*")
public class BoardController {
    public static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    BoardService boardService;

    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    CommentService commentService;

    @GetMapping("")
    ResponseEntity getAllBoards() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<Board> allBoard = boardService.getAllBoard();
            List<BoardGetDto> allBoardGetDto = new ArrayList<>();
            for (Board board :
                    allBoard) {
                allBoardGetDto.add(board.toBoardGetDto());
            }
            resultMap.put("content", allBoardGetDto);
        } catch (Exception e) {
            logger.error("게시글 목록 불러오기 실패 {}", e.toString());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(resultMap, status);
    }

    @GetMapping("/{board_no}")
    ResponseEntity getBoard(@PathVariable("board_no")int board_no) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultMap2 = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            Board board = boardService.getBoard(board_no);
            BoardGetDto dto = board.toBoardGetDto();
            boardService.setFileNo(dto, board);

            resultMap.put("board", dto);
            List<Comment> boardComments = commentService.getBoardComments(board.getNo());
            List<CommentGetDto> commentGetDtos = new ArrayList<>();
            for (Comment cmt :
                    boardComments) {
                commentGetDtos.add(cmt.toCommentGetDto());
            }
            resultMap.put("comments",commentGetDtos);

            resultMap2.put("content",resultMap);
        } catch (Exception e) {
            logger.error("게시글 불러오기 실패 {}", e.toString());
            resultMap2.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap2, status);
    }

//    @PostMapping("")
//    ResponseEntity writeBoard(@RequestBody BoardDto boardDto, @RequestHeader(value = "X-AUTH-TOKEN") String token) {
//
//        int user_no = Integer.parseInt(jwtTokenProvider.getUserPk(token));
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.OK;
//        try {
//            boardService.writeBoard(boardDto,user_no);
//        } catch (Exception e) {
//            logger.error("게시글 쓰기 실패 {}", e.toString());
//            resultMap.put("msg", e.getMessage());
//            status = HttpStatus.BAD_REQUEST;
//        }
//        return new ResponseEntity(resultMap, status);
//    }

    @ApiOperation(value="게시물 쓰기",notes="새로운 게시물을 씁니다. (파일 첨부는 한개만 가능)")
    @PostMapping("")
    ResponseEntity writeBoard(@ModelAttribute BoardDto boardDto, @RequestHeader(value = "X-AUTH-TOKEN") String token) {
        System.out.print("boardDto: "+boardDto);
        int user_no = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            BoardGetDto boardResult = boardService.writeBoard(boardDto, user_no).toBoardGetDto();
            resultMap.put("content",boardResult);
        } catch (Exception e) {
            logger.error("게시글 쓰기 실패 {}", e.toString());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap, status);
    }

//    @PutMapping("")
//    ResponseEntity updateBoard(@RequestBody BoardUpdateDto boardUpdateDto, @RequestHeader(value = "X-AUTH-TOKEN") String token) {
//        int user_no = Integer.parseInt(jwtTokenProvider.getUserPk(token));
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.OK;
//        try {
//            boardService.updateBoard(boardUpdateDto,user_no);
//        } catch (Exception e) {
//            logger.error("게시글 수정 실패 {}", e.toString());
//            resultMap.put("msg", e.getMessage());
//            status = HttpStatus.BAD_REQUEST;
//        }
//        return new ResponseEntity(resultMap, status);
//    }

    @PutMapping("")
    ResponseEntity updateBoard(@ModelAttribute BoardUpdateDto boardUpdateDto, @RequestHeader(value = "X-AUTH-TOKEN") String token) {
        System.out.println("boardUpdateDto: "+boardUpdateDto);
        int user_no = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            BoardGetDto boardResult = boardService.updateBoard(boardUpdateDto, user_no).toBoardGetDto();
            resultMap.put("content",boardResult);
        } catch (Exception e) {
            logger.error("게시글 수정 실패 {}", e.toString());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap, status);
    }

    @DeleteMapping("")
    ResponseEntity deleteBoard(@RequestBody BoardUpdateDto boardDto, @RequestHeader(value = "X-AUTH-TOKEN") String token) {
        int user_no = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            boardService.deleteBoard(boardDto.getNo(), user_no);
        } catch (Exception e) {
            logger.error("게시글 삭제 실패 {}", e.toString());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap, status);
    }

    @ApiOperation(value="게시판 첨부 파일 조회", notes="해당 게시판 첨부 파일의 이미지 파일을 반환")
    @GetMapping("/file/{no}")
    public ResponseEntity<byte[]> getBoardFile(@PathVariable int no) {

        try {
            File file = boardService.getFile(no);
            HttpHeaders header = new HttpHeaders();
            try {
                header.add("Content-Type", Files.probeContentType(file.toPath()));
                return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalInputException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="게시글 조회수 증가", notes="해당 게시글의 조회수를 1 증가시킵니다.")
    @PutMapping("/view/{board_no}")
    public ResponseEntity increaseHit(@PathVariable int board_no) {
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            int hit = boardService.increaseHit(board_no);
            resultMap.put("content",hit);
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("msg",e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity(resultMap,status);
    }
}
