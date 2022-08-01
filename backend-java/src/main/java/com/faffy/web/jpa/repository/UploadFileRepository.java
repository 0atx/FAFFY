package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Integer> {
}
