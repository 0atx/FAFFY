package com.faffy.web.service;

import com.faffy.web.dto.HistoryConsultingDto;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.repository.ConsultingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class ConsultingServiceImpl implements ConsultingService{
    @Autowired
    ConsultingRepository consultingRepository;

    private String getDuration(String startTime, String endTime){
        System.out.println("startTime:"+startTime);
        System.out.println("endTime:"+endTime);
        String[] sTime = startTime.substring(0, startTime.length()-2).split(":");
        String[] eTime = endTime.substring(0, startTime.length()-2).split(":");
        int stotal = 60*60*Integer.parseInt(sTime[0]) + 60*Integer.parseInt(sTime[1]) + Integer.parseInt(sTime[2]);
        int etotal = 60*60*Integer.parseInt(eTime[0]) + 60*Integer.parseInt(eTime[1]) + Integer.parseInt(eTime[2]);
        int dur = etotal - stotal;

        int hour = dur/3600, minute = (dur%3600)/60, second = dur%60;
        return hour + ":" + minute + ":" + second;
    }

    @Override
    public HistoryConsultingDto getHistoryConsulting(int no) throws IllegalInputException {
        Consulting consulting = consultingRepository.findById(no).orElse(null);
        if(consulting == null)
            throw new IllegalInputException();

        Timestamp startTimestamp = Timestamp.valueOf(consulting.getStartTime());
        Timestamp endTimestamp = Timestamp.valueOf(consulting.getEndTime());
        String sdate = startTimestamp.toString().split(" ")[0].replace(':', '-');

        String startTime = startTimestamp.toString().split(" ")[1];
        String endTime = endTimestamp.toString().split(" ")[1];
        String duration = getDuration(startTime, endTime);

        return HistoryConsultingDto.builder()
                .no(consulting.getNo())
                .title(consulting.getTitle())
                .date(sdate)
                .duration(duration)
                .startTime(startTime)
                .endTime(endTime)
                .introduce(consulting.getIntro())
                .categories(consulting.getCategories())
                .build();
    }

    @Override
    public File getSnapShots(int no) {
        return null;
    }
}
