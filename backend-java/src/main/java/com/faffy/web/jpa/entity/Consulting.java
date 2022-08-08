package com.faffy.web.jpa.entity;

import com.faffy.web.dto.ConsultingGetDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Consulting extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "consultant_no")
    private User consultant;

    @Column(nullable = false)
    private String title;
    private int roomSize;
    private int viewCount;

    @Column(length = 300)
    private String intro;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY, mappedBy = "consultingCategoryMapper.consulting")
    @JsonIgnore
    private List<ConsultingCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "consulting")
    @JsonIgnore
    private List<ConsultingFile> snapshots = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ConsultingGetDto toConsultingGetDto(){
        List<String> categories = new ArrayList<>();
        for(ConsultingCategory c : this.categories){
            categories.add(c.getConsultingCategoryMapper().getCategory().getName());
        }

        return ConsultingGetDto.builder()
                .no(this.no)
                .consultant(this.consultant.getNickname())
                .title(this.title)
                .introduce(this.intro)
                .roomSize(this.roomSize)
                .viewCount(this.viewCount)
                .categories(categories)
                .profileImageNo(consultant.getProfileImage().getNo())
                .build();
    }
}

