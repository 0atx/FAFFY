package com.faffy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BroadCastHistoryDto {
    private String title;
    private String intro;
    private String consultant; //닉네임
    private String date;
}
