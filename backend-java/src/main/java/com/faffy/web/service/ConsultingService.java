package com.faffy.web.service;

import com.faffy.web.dto.HistoryConsultingDto;
import com.faffy.web.exception.IllegalInputException;

import java.io.File;

public interface ConsultingService {
    HistoryConsultingDto getHistoryConsulting(int no) throws IllegalInputException;
    File getSnapShots(int no);
}
