package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.entity.ConsultingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultingRepository extends JpaRepository<Consulting, Integer> {
    @Query("select c from Consulting c where c.endTime is null order by c.viewCount desc")
    List<Consulting> findAllOrderByViewCount(Pageable pageable);
}
