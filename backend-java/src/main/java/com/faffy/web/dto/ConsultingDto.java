package com.faffy.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public class ConsultingDto {
    @Data
    public class ConsultingSnapshotUploadRequestDto {
        @NonNull
        private int consulting_no;
        @NonNull
        private MultipartFile file;
    }

    @Data
    @NoArgsConstructor
    public static class ConsultingFinishRequestDto {
        @NonNull
        private int consulting_no;
        // 컨설턴트의 no (호스트)
        @NonNull
        private int user_no;
    }
}
