package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.FashionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FashionCategoryRepository extends JpaRepository<FashionCategory, Integer> {
    FashionCategory findFashionCategoryByName(String name);
}
