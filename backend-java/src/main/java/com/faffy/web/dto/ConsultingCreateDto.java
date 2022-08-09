package com.faffy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ConsultingCreateDto {
    @NonNull
    private int consultant_no;
    @NonNull
    private String title;
    private String intro;
    @NonNull
    private int roomSize;
    private List<String> categories = new ArrayList<>();

    public ConsultingCreateDto() {
    }
}
