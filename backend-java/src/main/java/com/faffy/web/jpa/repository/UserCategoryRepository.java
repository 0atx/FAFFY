package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

}
