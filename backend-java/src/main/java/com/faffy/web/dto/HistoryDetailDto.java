package com.faffy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class HistoryDetailDto {
    private HistoryUserInfoDto userInfoDto;
    private HistoryConsultingDto consultingDto;
}
