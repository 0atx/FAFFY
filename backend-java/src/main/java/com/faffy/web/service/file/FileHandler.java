package com.faffy.web.service.file;

import com.faffy.web.jpa.entity.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class FileHandler {
    @Value("${spring.servlet.multipart.location}")
    private String FILE_PATH;

    /**
     * file 저장하고 UploadFile 엔티티 반환
     * @param img
     * @return UploadFile
     * @throws Exception
     */
    public UploadFile parseFileInfo(MultipartFile img) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = simpleDateFormat.format(new Date());
        String absPath = FILE_PATH + "\\";
        String path = "images/" + current_date;

        File file = new File(absPath + path);
        //저장할 디렉토리가 존재하지 않는 경우 새로 생성
        if(!file.exists()){
            System.out.println("===폴더 새로 생성===");
            file.mkdirs();
        }

        if(!img.isEmpty()){
            String contentType = img.getContentType();
            String originalFileExtension;

            //확장자 명이 없는 파일은 처리 안함
            if(ObjectUtils.isEmpty(contentType)){
                System.out.println("확장자 명 없음!!");
                return null;
            }

            if(contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            }
            else if(contentType.contains("image/png")){
                originalFileExtension = ".png";
            }
            else if(contentType.contains("image/gif")){
                originalFileExtension = ".gif";
            }
            else return null;

            String uuid = UUID.randomUUID().toString();
            String new_file_name = uuid + originalFileExtension;
            file = new File(absPath + path + "/" + new_file_name);
            img.transferTo(file);

            return UploadFile.builder()
                    .fileName(img.getOriginalFilename())
                    .uploadPath(absPath + path + "/" + new_file_name)
                    .uuid(uuid)
                    .build();
        }

        return null;
    }
}
