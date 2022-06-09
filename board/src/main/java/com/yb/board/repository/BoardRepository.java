package com.yb.board.repository;

import com.yb.board.Entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Query("select b from BoardEntity b where b.title like %:keyword%")
    List<BoardEntity> searchPostsByKeyword(@Param("keyword") String keyword);
}
