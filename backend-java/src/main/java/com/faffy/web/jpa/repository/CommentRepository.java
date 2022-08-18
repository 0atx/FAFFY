package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.Board;
import com.faffy.web.jpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    public List<Comment> findByBoard(Board board);
}
