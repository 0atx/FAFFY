package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.BoardFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
    @Query("select b from BoardFile b where b.board = :board")
    List<BoardFile> findAllWithBoard(@Param("board") Board board);

    @Query("select bf from BoardFile bf join fetch Board b on bf.board = b order by b.datetime desc")
    List<BoardFile> findAllOrderByDateWithImage(Pageable pageable);
}
