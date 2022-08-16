package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

    @Query("select u from User_Category u where u.userCategoryMapper.user=:user")
    List<UserCategory> findByUser(@Param("user") User user);
    @Query("select u.userCategoryMapper.category from User_Category u where u.userCategoryMapper.user = :user")
    List<FashionCategory> findCategoriesByUser(@Param("user") User user);

}
