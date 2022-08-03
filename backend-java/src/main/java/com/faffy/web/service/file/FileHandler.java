package com.faffy.web.service.file;

import com.faffy.web.jpa.entity.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
public class FileHandler {
    @Value("${file.upload.dir.window}")
    private String WIN_FILE_PATH;
    @Value("${file.upload.dir.linux}")
    private String LINUX_FILE_PATH;

    private final String sep = File.separator;
    /**
     * file 저장하고 UploadFile 엔티티 반환
     * @param img
     * @return UploadFile
     * @throws Exception
     */
    public UploadFile parseFileInfo(MultipartFile img) throws Exception{
        String absPath = null;
        if(System.getProperty("os.name").toLowerCase().contains("win"))
            absPath = WIN_FILE_PATH + "\\";
        else
            absPath = LINUX_FILE_PATH + "/";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String path = "profile_images" + sep + sdf.format(date).replace("-", sep);

        File file = new File(absPath + path);
        //저장할 디렉토리가 존재하지 않는 경우 새로 생성
        if(!file.exists()){
            System.out.println("===폴더 새로 생성===");
            file.mkdirs();
        }

        if(!img.isEmpty()){
            String contentType = img.getContentType();

            //확장자 명이 없는 파일은 처리 안함
            if(ObjectUtils.isEmpty(contentType)){
                System.out.println("확장자 명 없음!!");
                return null;
            }

            if(!contentType.contains("image/jpeg") && !contentType.contains("image/png") && !contentType.contains("image/gif"))
                return null;

            String uuid = UUID.randomUUID().toString();
            String new_file_name = uuid + "_" + img.getOriginalFilename();
            file = new File(absPath + path + sep + new_file_name);
            img.transferTo(file);

            return UploadFile.builder()
                    .fileName(img.getOriginalFilename())
                    .uploadPath(absPath + path)
                    .uuid(uuid)
                    .build();
        }

        return null;
    }

    public boolean deleteFile(UploadFile uf){
        String path = uf.getUploadPath() + uf.getUuid() + "_" +  uf.getFileName();
        File file = new File(path);
        return file.delete();
    }
}
