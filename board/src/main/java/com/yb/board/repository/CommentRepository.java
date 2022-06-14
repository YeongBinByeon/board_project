package com.yb.board.repository;

import com.yb.board.Entity.BoardEntity;
import com.yb.board.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("select c from CommentEntity c where c.boardEntity =:boardEntity")
    List<CommentEntity> searchCommentsByBoardId(@Param("boardEntity") BoardEntity boardEntity);
}
