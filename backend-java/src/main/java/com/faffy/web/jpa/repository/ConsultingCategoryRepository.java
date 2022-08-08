package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.ConsultingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultingCategoryRepository extends JpaRepository<ConsultingCategory, Integer> {

}
