package com.example.board.boardservice.repository;


import com.example.board.boardservice.dto.response.PostSummaryDto;
import com.example.board.boardservice.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 전체 게시글 조회 (PostSummaryDto 반환)
    @Query("""
        SELECT p.id AS id, p.title AS title, p.content AS content,
               p.createdDate AS createdDate, u.username AS username
        FROM Post p
        LEFT JOIN p.author u
        ORDER BY p.createdDate DESC
    """)
    List<PostSummaryDto> findAllByOrderByCreatedDateDesc();

    // 페이징을 위한 Post 조회 (엔티티 반환)
    List<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);

    // 특정 생성일 및 ID 이전 데이터 조회 (PostSummaryDto 반환)
    @Query("""
        SELECT p.id AS id, p.title AS title, p.content AS content,
               p.createdDate AS createdDate, u.username AS username
        FROM Post p
        LEFT JOIN p.author u
        WHERE p.createdDate < :createdDate
           OR (p.createdDate = :createdDate AND p.id < :id)
        ORDER BY p.createdDate DESC, p.id DESC
    """)
    List<PostSummaryDto> findPostsByCursor(
            @Param("createdDate") LocalDateTime createdDate,
            @Param("id") Long id,
            Pageable pageable
    );

    // 첫 번째 커서 요청 (PostSummaryDto 반환)
    @Query("""
        SELECT p.id AS id, p.title AS title, p.content AS content,
               p.createdDate AS createdDate, u.username AS username
        FROM Post p
        LEFT JOIN p.author u
        ORDER BY p.createdDate DESC
        LIMIT 10
    """)
    List<PostSummaryDto> findTop10ByOrderByCreatedDateDesc();
}