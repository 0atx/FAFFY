package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.FollowMapping;
import com.faffy.web.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowMappingRepository extends JpaRepository<FollowMapping, Integer> {
//    @Query("select f from FollowMapping f where f.followedUser=:followedUser")
//    List<FollowMapping> findUserFollower(@Param("followedUser") User followedUser);
//
//    @Query("select f from FollowMapping f where f.followUser=:followUser")
//    List<FollowMapping> findFollowing(@Param("followUser") User followUser);

    List<FollowMapping> findFollowMappingsByFollowedUser(User followedUser);
    List<FollowMapping> findFollowMappingsByFollowUser(User followUser);

    Optional<FollowMapping> findByFollowUserAndFollowedUser(User FollowUser,User followedUser);


}
