package com.faffy.web.service;

import com.faffy.web.dto.ConsultingCreateDto;
import com.faffy.web.dto.ConsultingDto;
import com.faffy.web.dto.ConsultingDto.ConsultingFinishRequestDto;
import com.faffy.web.dto.ConsultingDto.ConsultingSnapshotUploadRequestDto;
import com.faffy.web.dto.ConsultingDto.CreateLogRequestDto;
import com.faffy.web.dto.ConsultingGetDto;
import com.faffy.web.dto.HistoryConsultingDto;
import com.faffy.web.exception.DataNotFoundException;
import com.faffy.web.exception.ExceptionMsg;
import com.faffy.web.exception.IllegalInputException;
import com.faffy.web.jpa.entity.*;
import com.faffy.web.jpa.repository.*;
import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.entity.UploadFile;
import com.faffy.web.jpa.repository.ConsultingRepository;
import com.faffy.web.jpa.repository.UploadFileRepository;
import com.faffy.web.jpa.type.FileType;
import com.faffy.web.service.file.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.faffy.web.exception.ExceptionMsg.CONSULTING_NOT_FOUND_MSG;

@Service
public class ConsultingServiceImpl implements ConsultingService {
    @Autowired
    ConsultingRepository consultingRepository;
    @Autowired
    UploadFileRepository uploadFileRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FashionCategoryRepository fashionCategoryRepository;
    @Autowired
    ConsultingCategoryRepository consultingCategoryRepository;
    @Autowired
    ConsultingLogRepository consultingLogRepository;
    @Autowired
    ConsultingFileRepository consultingFileRepository;
    @Autowired
    FileHandler fileHandler;


    private String getDuration(String startTime, String endTime) {
        System.out.println("startTime:" + startTime);
        System.out.println("endTime:" + endTime);
        String[] sTime = startTime.substring(0, startTime.length() - 2).split(":");
        String[] eTime = endTime.substring(0, startTime.length() - 2).split(":");
        int stotal = 60 * 60 * Integer.parseInt(sTime[0]) + 60 * Integer.parseInt(sTime[1]) + Integer.parseInt(sTime[2]);
        int etotal = 60 * 60 * Integer.parseInt(eTime[0]) + 60 * Integer.parseInt(eTime[1]) + Integer.parseInt(eTime[2]);
        int dur = etotal - stotal;

        int hour = dur / 3600, minute = (dur % 3600) / 60, second = dur % 60;
        return hour + ":" + minute + ":" + second;
    }

    @Override
    public HistoryConsultingDto getHistoryConsulting(int no) throws IllegalInputException {
        Consulting consulting = consultingRepository.findById(no).orElse(null);
        if (consulting == null)
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
                .fileList(consulting.getSnapshots())
                .build();
    }

    @Override
    public File getSnapshot(int no) throws IllegalInputException {
        UploadFile uf = uploadFileRepository.findById(no).orElse(null);
        if (uf == null)
            throw new IllegalInputException();

        String filename = uf.getUploadPath() + File.separator + uf.getUuid() + "_" + uf.getFileName();
        return new File(filename);
    }

    @Override
    public Consulting getConsulting(int consulting_no) throws Exception {
        Consulting consulting = consultingRepository.findById(consulting_no).orElseThrow(() -> new DataNotFoundException(CONSULTING_NOT_FOUND_MSG));
        return consulting;
    }

    @Override
    public List<ConsultingGetDto> getConsultingsByViewCount(Pageable pageable) {
        List<Consulting> consultings = consultingRepository.findAllOrderByViewCount(pageable);
        List<ConsultingGetDto> dtoList = new ArrayList<>();
        for (Consulting c : consultings) {
            dtoList.add(c.toConsultingGetDto());
        }
        return dtoList;
    }

    @Override
    public List<ConsultingGetDto> getLatestConsultings(Pageable pageable) {
        List<Consulting> consultings = consultingRepository.findAllOrderByStartTime(pageable);
        List<ConsultingGetDto> dtoList = new ArrayList<>();
        for (Consulting c : consultings) {
            dtoList.add(c.toConsultingGetDto());
        }
        return dtoList;
    }

    @Override
    public List<ConsultingGetDto> getConsultingsByDate() {
        List<Consulting> consultings = consultingRepository.findAllLatest();
        List<ConsultingGetDto> dtoList = new ArrayList<>();
        for(Consulting consulting : consultings){
            dtoList.add(consulting.toConsultingGetDto());
        }
        return dtoList;
    }

    @Override
    @Transactional
    public ConsultingGetDto createConsulting(ConsultingCreateDto dto) throws Exception {
        // 이미 생성된 방송이 있으면 에러 발생해야함

        User user = userRepository.findByNo(dto.getConsultant_no()).orElse(null);
        if (user == null)
            throw new DataNotFoundException(ExceptionMsg.USER_NOT_FOUND_MSG);

        Consulting consulting = Consulting.builder()
                .consultant(user)
                .title(dto.getTitle())
                .intro(dto.getIntro())
                .roomSize(dto.getRoomSize())
                .startTime(LocalDateTime.now())
                .build();

        consultingRepository.save(consulting);
        for (String c : dto.getCategories()) {
            FashionCategory fashionCategory = fashionCategoryRepository.findByName(c).orElse(null);
            if (fashionCategory == null) //저장되지 않은 카테고리면 설정하지 않음
                continue;

            ConsultingCategory cc = ConsultingCategory.builder().category(fashionCategory).consulting(consulting).build();
            consultingCategoryRepository.save(cc);
        }
        return consulting.toConsultingGetDto();
//        return ConsultingGetDto.builder().build(); // 성공시 빈 객체 반환
    }

    @Override
    public void createLog(CreateLogRequestDto logDto) throws IllegalInputException {
        Consulting consulting = consultingRepository.findById(logDto.getConsulting_no()).orElse(null);
        User user = userRepository.findByNo(logDto.getUser_no()).orElse(null);
        if (consulting == null || user == null) {
            throw new IllegalInputException();
        }

        // 이미 참여한 기록이 있다면 다시 저장하지 않음
        ConsultingLog log = ConsultingLog.builder().consulting(consulting).user(user).build();
        if (consultingLogRepository.findConsultingLogByUserAndConsulting(user,consulting).isPresent())
            return;

        consultingLogRepository.save(log);
    }

    @Override
    @Transactional
    public void setViewCount(int no, int cnt) throws IllegalInputException {
        Consulting consulting = consultingRepository.findById(no).orElse(null);
        if (consulting == null)
            throw new IllegalInputException();

        consulting.setViewCount(cnt);
    }

    @Transactional
    public int uploadSnapshot(ConsultingSnapshotUploadRequestDto dto) throws Exception {
        Consulting consulting = consultingRepository.findById(dto.getConsulting_no()).orElseThrow(() -> new IllegalArgumentException(CONSULTING_NOT_FOUND_MSG));
        UploadFile img = fileHandler.parseFileInfo(dto.getFile(), FileType.SNAPSHOT);

        if (img == null)
            throw new Exception("이미지 파싱 에러");

        uploadFileRepository.save(img);

        ConsultingFile consultingFile = ConsultingFile.builder()
                .consulting(consulting)
                .file(img)
                .build();
        consultingFileRepository.save(consultingFile);

        return consultingFile.getNo();
    }

    @Override
    @Transactional
    public void finishConsulting(ConsultingFinishRequestDto finishDto) throws Exception {
        // 먼저 컨설팅 찾기
        Consulting consulting = getConsulting(finishDto.getConsulting_no());
        // 컨설팅 종료 날짜 현재 시각으로 설정
        consulting.finish();
        // 현재 보고있는 시청자수 0으로 하는건 딱히 의미 없나?
    }

    @Override
    public List<ConsultingGetDto> searchByKeyword(String keyword) throws Exception{
        List<Consulting> consultings = consultingRepository.findByKeyword(keyword);
        List<ConsultingGetDto> dtoList = new ArrayList<>();
        for(Consulting c : consultings){
            if(c.getEndTime() != null) continue;
            List<ConsultingCategory> categoryList = consultingCategoryRepository.findByConsulting(c);
            List<String> categories = new ArrayList<>();
            for(ConsultingCategory cc : categoryList){
                categories.add(cc.getConsultingCategoryMapper().getCategory().getName());
            }
            ConsultingGetDto dto = c.toConsultingGetDto();
            dto.setCategories(categories);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<Integer> getSnapshotList(int consulting_no) throws Exception {
        Consulting consulting = getConsulting(consulting_no);
        List<Integer> snapshotList = new ArrayList<>();
        for (ConsultingFile file : consulting.getSnapshots()) {
            snapshotList.add(file.getFile().getNo());
        }
        return snapshotList;
    }
}
