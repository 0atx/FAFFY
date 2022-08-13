package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.entity.ConsultingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface ConsultingRepository extends JpaRepository<Consulting, Integer> {
    @Query("select c from Consulting c where c.endTime is null order by c.viewCount desc")
    List<Consulting> findAllOrderByViewCount(Pageable pageable);
    @Query("select c from Consulting c where c.endTime is null order by c.startTime desc")
    List<Consulting> findAllOrderByStartTime(Pageable pageable);

    @Query("select distinct c from Consulting c join fetch c.categories cc join fetch cc.consultingCategoryMapper.category fc " +
            "where c.title like concat('%',:keyword,'%') " +
            "or c.intro like concat('%',:keyword,'%') " +
            "or c.consultant.nickname like concat('%',:keyword,'%') " +
            "or fc.name like concat('%',:keyword,'%')")
    List<Consulting> findByKeyword(String keyword) throws SQLException;
}
