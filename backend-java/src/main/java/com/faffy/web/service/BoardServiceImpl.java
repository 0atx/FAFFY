package com.faffy.web.service;

import com.faffy.web.dto.BoardDto;
import com.faffy.web.dto.BoardGetDto;
import com.faffy.web.dto.BoardUpdateDto;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.BoardFile;
import com.faffy.web.jpa.entity.UploadFile;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.BoardFileRepository;
import com.faffy.web.jpa.repository.BoardRepository;
import com.faffy.web.jpa.repository.UploadFileRepository;
import com.faffy.web.service.file.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.faffy.web.exception.ExceptionMsg.BOARD_NOT_FOUND_MSG;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardFileRepository boardFileRepository;
    @Autowired
    UploadFileRepository uploadFileRepository;

    @Autowired
    UserService userService;
    @Autowired
    FileHandler fileHandler;


    @Override
    public List<Board> getAllBoard() {
        return boardRepository.findAllWithUser();
    }

    @Override
    @Transactional
    public void writeBoard(BoardDto boardDto, int user_no) throws Exception {
        boardDto.setWriter_no(user_no);
        User u = userService.getUserByNo(user_no);
        Board b = boardDto.toEntityWriteBy(u);
        if(u == null) {
            System.out.println("글쓰기 실패!!! - 존재하지 않는 작성자.");
            throw new Exception();
        }

        boardRepository.save(b);
        MultipartFile file = boardDto.getFile();
        if(file != null){ //선택한 파일이 있는 경우
            System.out.println("===There are uploaded files===");
            UploadFile uf = null;
            try {
                uf = fileHandler.parseFileInfo(file, "board");
            } catch (Exception e) {
                e.printStackTrace();
            }

            uploadFileRepository.save(uf);
            BoardFile bf = BoardFile.builder()
                    .board(b)
                    .file(uf)
                    .build();
            boardFileRepository.save(bf);
        }
    }

    @Override
    @Transactional
    public void updateBoard(BoardUpdateDto boardDto, int user_no) throws Exception {
        Board board = getBoard(boardDto.getNo());
        if(user_no!=board.getUser().getNo()){
            throw new Exception("글쓴이와 로그인 정보가 일치하지 않습니다.");
        }
        board.updateBoard(boardDto);

        //첨부 파일 업데이트 - 기존 첨부 되어있는 파일 모두 삭제하고 새로 업로드
        List<BoardFile> bfList = boardFileRepository.findAllWithBoard(board);
        if(!bfList.isEmpty()) {
            UploadFile uploadFile = bfList.get(0).getFile();
            boardFileRepository.deleteAll(bfList);
            uploadFileRepository.delete(uploadFile);
            fileHandler.deleteFile(uploadFile);
        }

        MultipartFile file = boardDto.getFile();
        if(file != null){ //선택한 파일이 있는 경우
            System.out.println("===There are uploaded files===");
            UploadFile uf = null;
            try {
                uf = fileHandler.parseFileInfo(file, "board");
            } catch (Exception e) {
                e.printStackTrace();
            }

            uploadFileRepository.save(uf);
            BoardFile bf = BoardFile.builder()
                    .board(board)
                    .file(uf)
                    .build();
            boardFileRepository.save(bf);
        }
    }

    @Override
    @Transactional
    public void deleteBoard(int no, int user_no) throws Exception {
        Board board = getBoard(no);
        if(!(user_no==board.getUser().getNo())){
            throw new Exception("글쓴이와 로그인 정보가 일치하지 않습니다.");
        }
        try {
            List<BoardFile> bfList = boardFileRepository.findAllWithBoard(board);
            if(!bfList.isEmpty()) {
                UploadFile uploadFile = bfList.get(0).getFile();
                boardFileRepository.deleteAll(bfList);
                uploadFileRepository.delete(uploadFile);
                fileHandler.deleteFile(uploadFile);
            }

            boardRepository.deleteById(no);
        } catch (Exception e) {
            throw new Exception(BOARD_NOT_FOUND_MSG);
        }
    }

    @Override
    @Transactional
    public Board getBoard(int boardNo) {
        Board board = boardRepository.findByNoWithUser(boardNo).orElseThrow(() -> new IllegalArgumentException(BOARD_NOT_FOUND_MSG));
        board.increaseHit();
        return board;
    }

    @Override
    public void setFileNo(BoardGetDto dto, Board board) {
        List<BoardFile> bfList = boardFileRepository.findAllWithBoard(board);
        if(!bfList.isEmpty())
            dto.setFileNo(bfList.get(0).getFile().getNo());
    }

    @Override
    public File getFile(int no) throws IllegalInputException {
        UploadFile uf = uploadFileRepository.findById(no).orElse(null);
        if(uf == null)
            throw new IllegalInputException();

        String filename = uf.getUploadPath() + File.separator + uf.getUuid() + "_" + uf.getFileName();
        return new File(filename);
    }

    @Override
    public List<BoardGetDto> getBoardsByDate(Pageable pageable) {
        List<Board> boardList = boardRepository.findAllOrderByDate(pageable);
        List<BoardGetDto> result = new ArrayList<>();
        for(Board b : boardList){
            result.add(b.toBoardGetDto());
        }
        return result;
    }

    @Override
    public List<BoardGetDto> getBoardsByHit(Pageable pageable) {
        List<Board> boardList = boardRepository.findAllOrderByHit(pageable);
        List<BoardGetDto> result = new ArrayList<>();
        for(Board b : boardList){
            result.add(b.toBoardGetDto());
        }
        return result;
    }

    @Override
    public List<Integer> getBoardsImageNoByDate(Pageable pageable) {
        List<BoardFile> boardFiles = boardFileRepository.findAllOrderByDateWithImage(pageable);
        List<Integer> res = new ArrayList<>();
        for(BoardFile bf : boardFiles){
            res.add(bf.getFile().getNo());
        }
        return res;
    }

}
