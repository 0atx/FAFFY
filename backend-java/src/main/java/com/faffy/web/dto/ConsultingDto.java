package com.faffy.web.dto;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public class ConsultingDto {
    @Data
    public class ConsultingSnapshotUploadDto {
        @NonNull
        private int consulting_no;
        @NonNull
        private MultipartFile file;
    }
}
