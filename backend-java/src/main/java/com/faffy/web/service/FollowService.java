package com.faffy.web.service;

import com.faffy.web.dto.UserGetDetailDto;

import java.util.List;

public interface FollowService {

    // 팔로우 목록 보기
    List<UserGetDetailDto> getFollowing(int user_no) throws Exception;

    // 팔로워 목록 보기
    List<UserGetDetailDto> getUserFollower(int user_no) throws Exception;
    // 팔로우 하기
    String follow(int from_no,int to_no) throws Exception;
    // 팔로우 취소
    String followCancel(int from_no, int to_no) throws Exception;

}
