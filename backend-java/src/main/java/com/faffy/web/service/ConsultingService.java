package com.faffy.web.service;

import com.faffy.web.dto.ConsultingCreateDto;
import com.faffy.web.dto.ConsultingDto;
import com.faffy.web.dto.ConsultingDto.ConsultingSnapshotUploadDto;
import com.faffy.web.dto.ConsultingGetDto;
import com.faffy.web.dto.HistoryConsultingDto;
import com.faffy.web.exception.IllegalInputException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.List;

public interface ConsultingService {
    HistoryConsultingDto getHistoryConsulting(int no) throws IllegalInputException;
    File getSnapshot(int no) throws IllegalInputException;


    List<ConsultingGetDto> getConsultingsByViewCount(Pageable pageable);

    ConsultingGetDto createConsulting(ConsultingCreateDto consultingCreateDto, int no);

    void createLog(int consulting_no, int user_no) throws IllegalInputException;

    void upViewCount(int no) throws IllegalInputException;
    String uploadSnapshot(ConsultingSnapshotUploadDto file) throws Exception;

}
