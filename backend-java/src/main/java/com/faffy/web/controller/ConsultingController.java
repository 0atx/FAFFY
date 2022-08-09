package com.faffy.web.controller;

import com.faffy.web.dto.ConsultingCreateDto;
import com.faffy.web.dto.ConsultingDto;
import com.faffy.web.dto.ConsultingDto.ConsultingSnapshotUploadDto;
import com.faffy.web.dto.ConsultingGetDto;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.service.ConsultingService;
import com.faffy.web.service.ConsultingServiceImpl;
import com.faffy.web.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/consultings")
@CrossOrigin("*")
public class ConsultingController {
    @Autowired
    ConsultingServiceImpl consultingService;
    @Autowired
    UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(ConsultingController.class);


    @ApiOperation(value="방송 스냅샷 조회", notes="해당 방송 스냅샷의 이미지 파일을 반환")
    @GetMapping("/snapshot/{no}")
    public ResponseEntity<byte[]> getConsultingSnapshot(@PathVariable int no) {

        try {
            File snapshot = consultingService.getSnapshot(no);
            HttpHeaders header = new HttpHeaders();
            try {
                header.add("Content-Type", Files.probeContentType(snapshot.toPath()));
                return new ResponseEntity<>(FileCopyUtils.copyToByteArray(snapshot), header, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalInputException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value="새로운 방송 정보 생성", notes="해당 유저를 consultant로 하여 새로운 방송 정보 db에 생성")
    @PostMapping("")
    public ResponseEntity createConsulting(@RequestBody ConsultingCreateDto consultingCreateDto,
                                                             @RequestParam int no) { //파라미터를 Token 정보로 바꿔야 함.
        ConsultingGetDto dto = consultingService.createConsulting(consultingCreateDto, no);
        if (dto == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="방송 입장 시 로그 생성", notes="방송 입장 시에 로그 정보 기록, 시청자 수 증가시키기")
    @PostMapping("/log")
    public ResponseEntity createConsultingLog(int consulting_no, int user_no) {
        try {
            consultingService.createLog(consulting_no, user_no);
        } catch(Exception e){
            HashMap<String, Object> result = new HashMap<>();
            result.put("msg", "유효하지 않은 방송 또는 유저입니다.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value="현재 방의 시청자 수를 재설정", notes="")
    @PostMapping("/viewCount")
    public ResponseEntity setConsultingViewCount(int consulting_no, int count) {
        try {
            consultingService.setViewCount(consulting_no, count);
        } catch(Exception e){
            HashMap<String, Object> result = new HashMap<>();
            result.put("msg", "유효하지 않은 방송입니다.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/snapshot")
    public ResponseEntity uploadSnapshot(@ModelAttribute ConsultingSnapshotUploadDto uploadDto) {
        logger.info("file : {}",uploadDto.toString());
        HttpStatus status;
        try {
            consultingService.uploadSnapshot(uploadDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            System.out.println("error:"+e.getMessage());
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(status);
    }
}
