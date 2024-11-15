package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import java.util.List;

public interface PostService {
    // 생성
    Post createPost(PostDto postDto);

    // 조회
    Post getPost(Long id);
    List<Post> getAllPosts();
    List<Post> getPostsByOffset(int page, int size);


    // 수정
    Post updatePost(Long id, PostDto postDto);

    // 삭제
    void deletePost(Long id);
}
