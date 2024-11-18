package com.example.board.boardservice.repository;


import com.example.board.boardservice.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>  {
    List<Post> findAllByOrderByCreatedDateDesc();
    List<Post> findByCreatedDateBeforeOrderByCreatedDateDesc(LocalDateTime cursorDate, Pageable pageable);
    List<Post> findAllByOrderByIdDesc();
}
