package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {
    @Query("select b from Board b join fetch b.user")
    List<Board> findAllWithUser();

    @Query("select b from Board b join fetch b.user where b.no=:no")
    Optional<Board> findByNoWithUser(@Param("no") int no);

    @Query("select b from Board b order by b.datetime desc")
    List<Board> findAllOrderByDate(Pageable pageable);

    @Query("select b from Board b order by b.hit desc, b.datetime desc")
    List<Board> findAllOrderByHit(Pageable pageable);

    @Query("select b from Board b where b.title like concat('%', :keyword, '%') " +
            "or b.content like concat('%', :keyword, '%') " +
            "or b.user.nickname like concat('%', :keyword, '%')")
    List<Board> findByKeyword(String keyword) throws SQLException;
}
