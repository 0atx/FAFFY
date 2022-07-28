package com.faffy.web.service;

import com.faffy.web.dto.UserDto;
import com.faffy.web.dto.UserPublicDto;
import com.faffy.web.exception.DataNotFoundException;

import java.util.List;

public interface FollowService {

    // 팔로우 목록 보기
    List<UserPublicDto> getFollowing(int user_no) throws Exception;

    // 팔로워 목록 보기
    List<UserPublicDto> getUserFollower(int user_no) throws Exception;
    // 팔로우 하기
    String follow(int from_no,int to_no) throws Exception;
    // 팔로우 취소
    String followCancel(int from_no, int to_no) throws Exception;

}
