package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.CursorDto;
import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.entity.User;
import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    // 생성
    Post createPost(PostDto postDto, User user);

    // 조회
    Post getPost(Long id);
    List<Post> getAllPosts();
    // offset 기반 페이징
    List<Post> findPostsByOffset(int page);
    // cursor 기반 페이징
    CursorDto<Post> findPostsByCursor(LocalDateTime localDateTime, Long cursor);
    CursorDto<Post> firstPostsByCursor();


    // 수정
    Post updatePost(Long id, PostDto postDto, User user);

    // 삭제
    void deletePost(Long id, User user);
}
