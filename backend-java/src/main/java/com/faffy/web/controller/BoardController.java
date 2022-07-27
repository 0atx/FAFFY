package com.faffy.web.controller;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.BoardGetDto;
import com.faffy.web.dto.BoardUpdateDto;
import com.faffy.web.dto.CommentGetDto;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.service.BoardService;
import com.faffy.web.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin("*")
public class BoardController {
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    BoardService boardService;

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
            resultMap.put("board", board);
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

    @PostMapping("")
    ResponseEntity writeBoard(@RequestBody BoardDto boardDto) {
        // user_no 는 나중에 jwt 헤더에서 추출해야함
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            boardService.writeBoard(boardDto);
        } catch (Exception e) {
            logger.error("게시글 쓰기 실패 {}", e.toString());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap, status);
    }

    @PutMapping("")
    ResponseEntity updateBoard(@RequestBody BoardUpdateDto boardUpdateDto) {
        // writer_no가 jwt 헤더에 있는 user_no와 일치하는지 검증 필요
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            boardService.updateBoard(boardUpdateDto);
        } catch (Exception e) {
            logger.error("게시글 수정 실패 {}", e.toString());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap, status);
    }

    @DeleteMapping("")
    ResponseEntity deleteBoard(@RequestBody BoardUpdateDto boardDto) {
        // writer_no가 jwt 헤더에 있는 user_no와 일치하는지 검증 필요
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            boardService.deleteBoard(boardDto.getNo());
        } catch (Exception e) {
            logger.error("게시글 삭제 실패 {}", e.toString());
            resultMap.put("msg", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(resultMap, status);
    }
}
