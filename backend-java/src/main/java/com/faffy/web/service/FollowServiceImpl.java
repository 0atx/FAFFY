package com.faffy.web.service;

import com.faffy.web.dto.UserGetDetailDto;
import com.faffy.web.jpa.entity.FollowMapping;
import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.repository.FollowMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.faffy.web.exception.ExceptionMsg.USER_NOT_FOUND_MSG;

@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
    FollowMappingRepository followMappingRepository;
    @Autowired
    UserService userService;
    @Override
    public List<UserGetDetailDto> getFollowing(int user_no) throws Exception {
        User user = userService.getUserByNo(user_no);
        List<FollowMapping> following = followMappingRepository.findFollowMappingsByFollowUser(user);

        List<UserGetDetailDto> result = new ArrayList<>();
        for (FollowMapping follow : following)
            result.add(follow.getFollowedUser().toDetailDto());

        return result;
    }

    @Override
    public List<UserGetDetailDto> getUserFollower(int user_no) throws Exception {
        User user = userService.getUserByNo(user_no);
        List<FollowMapping> follower = followMappingRepository.findFollowMappingsByFollowedUser(user);
        List<UserGetDetailDto> result = new ArrayList<>();
        for (FollowMapping follow : follower)
            result.add(follow.getFollowUser().toDetailDto());
        return result;
    }

    @Override
    public String follow(int from_no, int to_no) throws Exception {
        User from = userService.getUserByNo(from_no);
        User to = userService.getUserByNo(to_no);
        FollowMapping mapping = new FollowMapping();
        mapping.setMapping(from, to);
        followMappingRepository.save(mapping);
        return to.getNickname();
    }

    @Override
    public String followCancel(int from_no, int to_no) throws Exception {
        User from = userService.getUserByNo(from_no);
        User to = userService.getUserByNo(to_no);
        FollowMapping mapping = followMappingRepository.findByFollowUserAndFollowedUser(from,to).orElseThrow(
                ()->new IllegalArgumentException(USER_NOT_FOUND_MSG));
        followMappingRepository.delete(mapping);
        return to.getNickname();
    }
}
