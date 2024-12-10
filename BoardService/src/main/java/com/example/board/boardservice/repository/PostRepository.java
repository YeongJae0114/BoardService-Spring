package com.example.board.boardservice.repository;


import com.example.board.boardservice.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>  {
    List<Post> findAllByOrderByCreatedDateDesc();

    List<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);

    // 특정 생성일 및 ID 이전 데이터 조회
    @Query("""
        SELECT p FROM Post p
        WHERE p.createdDate < :createdDate
           OR (p.createdDate = :createdDate AND p.id < :id)
        ORDER BY p.createdDate DESC, p.id DESC
    """)
    List<Post> findPostsByCursor(
            @Param("createdDate") LocalDateTime createdDate,
            @Param("id") Long id,
            Pageable pageable
    );

    // 첫 번째 커서 요청
    List<Post> findTop10ByOrderByCreatedDateDesc();

}
