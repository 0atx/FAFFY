package com.faffy.web.controller;

import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.service.ConsultingService;
import com.faffy.web.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("/api/consultings")
@CrossOrigin("*")
public class ConsultingController {
    @Autowired
    ConsultingService consultingService;
    @Autowired
    UserService userService;

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
}
