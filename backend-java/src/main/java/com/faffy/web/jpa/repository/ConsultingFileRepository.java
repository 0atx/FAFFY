package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.ConsultingFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultingFileRepository extends JpaRepository<ConsultingFile,Integer> {
}
