package com.faffy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ConsultingCreateDto {
    private String title;
    private String introduce;
    private int roomSize;
    private List<String> categories = new ArrayList<>();
}
