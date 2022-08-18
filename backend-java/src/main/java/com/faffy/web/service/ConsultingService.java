package com.faffy.web.service;

import com.faffy.web.dto.ConsultingCreateDto;
import com.faffy.web.dto.ConsultingDto;
import com.faffy.web.dto.ConsultingDto.ConsultingFinishRequestDto;
import com.faffy.web.dto.ConsultingDto.ConsultingSnapshotUploadRequestDto;
import com.faffy.web.dto.ConsultingGetDto;
import com.faffy.web.dto.HistoryConsultingDto;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.Consulting;
import org.springframework.data.domain.Pageable;


import java.io.File;
import java.util.List;

public interface ConsultingService {
    HistoryConsultingDto getHistoryConsulting(int no) throws IllegalInputException;
    File getSnapshot(int no) throws IllegalInputException;

    Consulting getConsulting(int consulting_no) throws Exception;
    List<ConsultingGetDto> getConsultingsByViewCount(Pageable pageable);
    List<ConsultingGetDto> getLatestConsultings(Pageable pageable);
    List<ConsultingGetDto> getConsultingsByDate();


    ConsultingGetDto createConsulting(ConsultingCreateDto consultingCreateDto) throws Exception;

    void createLog(ConsultingDto.CreateLogRequestDto logDto) throws IllegalInputException;

    void setViewCount(int no, int cnt) throws IllegalInputException;
    int uploadSnapshot(ConsultingSnapshotUploadRequestDto file) throws Exception;

    void finishConsulting(ConsultingFinishRequestDto deleteDto) throws  Exception;

    List<ConsultingGetDto> searchByKeyword(String keyword) throws Exception;
    List<Integer> getSnapshotList(int consulting_no) throws Exception;
}
