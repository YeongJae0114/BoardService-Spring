package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import java.util.List;

public interface PostService {
    // 생성
    void create(PostDto postDto);

    // 수정
    void update(Long postId, PostDto postDto);

    // 삭제
    void delete(Long postId);

    // 게시글 조회 (단일)
    PostDto getPost(Long postId);

    // 게시글 목록 조회
    List<Post> getAllPosts();
}
