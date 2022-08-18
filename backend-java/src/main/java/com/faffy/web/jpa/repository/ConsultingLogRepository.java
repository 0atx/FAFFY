package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.entity.ConsultingLog;
import com.faffy.web.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultingLogRepository extends JpaRepository<ConsultingLog, Integer> {
    @Query("select c from ConsultingLog c where c.user.no = :no")
    Optional<List<ConsultingLog>> findConsultingLogsByUserNo(@Param("no") int userNo);

    @Query("select log from ConsultingLog log join fetch log.consulting c join fetch log.user u " +
            "where u.no = :no order by c.startTime desc")
    List<ConsultingLog> findConsultingHistoryByUserNo(@Param("no") int UserNo);

    Optional<ConsultingLog> findConsultingLogByUserAndConsulting(User user, Consulting consulting);
}
