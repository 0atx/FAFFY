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
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void writeBoard(BoardDto boardDto,int user_no) throws Exception {
        boardDto.setWriter_no(user_no);
        Board b = boardRepository.findById(boardDto.getNo()).orElse(null);
        User u = userService.getUserByNo(boardDto.getWriter_no());
        if(b == null || u == null)
            throw new Exception();

        List<MultipartFile> files = boardDto.getFiles();
        if(!files.isEmpty()){ //선택한 파일이 있으면
            System.out.println("===There are uploaded files===");
            List<UploadFile> uploadFiles = new ArrayList<>();
            for(MultipartFile file : files) {
                try {
                    UploadFile uf = fileHandler.parseFileInfo(file, "board");
                    if (uf != null)
                        uploadFiles.add(uf);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            uploadFileRepository.saveAll(uploadFiles);
            for(UploadFile uf : uploadFiles){
                BoardFile bf = BoardFile.builder()
                        .board(b)
                        .file(uf)
                        .build();
                boardFileRepository.save(bf);
            }
        }
        boardRepository.save(boardDto.toEntityWriteBy(u));
    }

    @Override
    @Transactional
    public void updateBoard(BoardUpdateDto boardDto, int user_no) throws Exception {
        Board board = getBoard(boardDto.getNo());
        if(!(user_no==board.getUser().getNo())){
            throw new Exception("글쓴이와 로그인 정보가 일치하지 않습니다.");
        }
        board.updateBoard(boardDto);

        //첨부 파일 업데이트 - 기존 첨부 되어있는 파일 모두 삭제하고 새로 업로드
        List<UploadFile> ufList = new ArrayList<>();
        List<BoardFile> bfList = boardFileRepository.findAllWithBoard(board);
        for(BoardFile bf : bfList){
            ufList.add(bf.getFile());
        }
        boardFileRepository.deleteAll(bfList);
        uploadFileRepository.deleteAll(ufList);
        for(UploadFile uf : ufList){
            fileHandler.deleteFile(uf);
        }

        List<MultipartFile> files = boardDto.getFiles();
        if(!files.isEmpty()){
            System.out.println("===There are uploaded files===");
            List<UploadFile> uploadFiles = new ArrayList<>();
            for(MultipartFile file : files) {
                try {
                    UploadFile uf = fileHandler.parseFileInfo(file, "board");
                    if (uf != null)
                        uploadFiles.add(uf);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            uploadFileRepository.saveAll(uploadFiles);
            for(UploadFile uf : uploadFiles){
                BoardFile bf = BoardFile.builder()
                        .board(board)
                        .file(uf)
                        .build();
                boardFileRepository.save(bf);
            }
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
    public void setFileNos(BoardGetDto dto, Board board) {
        List<BoardFile> bfList = boardFileRepository.findAllWithBoard(board);
        List<Integer> fileNoList = new ArrayList<>();
        for(BoardFile bf : bfList){
            fileNoList.add(bf.getFile().getNo());
        }
        dto.setFileNos(fileNoList);
    }

    @Override
    public File getFile(int no) throws IllegalInputException {
        UploadFile uf = uploadFileRepository.findById(no).orElse(null);
        if(uf == null)
            throw new IllegalInputException();

        String filename = uf.getUploadPath() + File.separator + uf.getUuid() + "_" + uf.getFileName();
        return new File(filename);
    }

}
