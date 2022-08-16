package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.entity.ConsultingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultingCategoryRepository extends JpaRepository<ConsultingCategory, Integer> {

    @Query("select cc from ConsultingCategory cc join fetch cc.consultingCategoryMapper.consulting fc " +
            "where cc.consultingCategoryMapper.consulting = :consulting")
    List<ConsultingCategory> findByConsulting(Consulting consulting);
}
