package com.faffy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ConsultingGetDto {
    private int no;
    private String title;
    private String intro;
    private int roomSize;
    private int viewCount;
    private String consultant; //닉네임
    private int consultant_no;
    private List<String> categories = new ArrayList<>();
    private int profileImageNo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
}
