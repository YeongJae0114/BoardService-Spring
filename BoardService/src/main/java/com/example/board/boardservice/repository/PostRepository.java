package com.example.board.boardservice.repository;


import com.example.board.boardservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>  {
}
