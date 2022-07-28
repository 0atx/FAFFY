package com.faffy.web.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserCategoryRequestDto {
    @NonNull
    private int user_no;
    @NonNull
    private String category_name;
}
