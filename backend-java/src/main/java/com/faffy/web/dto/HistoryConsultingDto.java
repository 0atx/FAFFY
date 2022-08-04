package com.faffy.web.dto;

import com.faffy.web.jpa.entity.ConsultingCategory;
import com.faffy.web.jpa.entity.UserCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class HistoryConsultingDto {
    private int no;
    private String title;
    private String date;
    private String duration;
    private String startTime;
    private String endTime;
    private String introduce;
    private List<String> categories = new ArrayList<>();

    @Builder
    public HistoryConsultingDto(int no, String title, String date, String duration, String startTime, String endTime, String introduce, List<ConsultingCategory> categories) {
        this.no = no;
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.introduce = introduce;

        for (ConsultingCategory category : categories) {
            this.categories.add(category.getConsultingCategoryMapper().getCategory().getName());
        }
    }
}
