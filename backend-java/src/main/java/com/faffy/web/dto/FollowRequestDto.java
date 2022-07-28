package com.faffy.web.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class FollowRequestDto {
    @NonNull
    private int from;
    @NonNull
    private int to;
    public FollowRequestDto() {}
}
