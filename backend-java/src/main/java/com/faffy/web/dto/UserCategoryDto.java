package com.faffy.web.dto;

import lombok.Data;

@Data
public class UserCategoryDto {
    private int no;
    private UserDto userDto;
    private FashionCategoryDto fashionCategoryDto;

    public UserCategoryDto() {}
    public UserCategoryDto(UserDto userDto, FashionCategoryDto fashionCategoryDto) {
        this.userDto = userDto;
        this.fashionCategoryDto = fashionCategoryDto;
    }
}
