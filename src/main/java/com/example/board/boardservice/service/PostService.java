package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.CursorDto;
import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.dto.response.PostSummaryDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.entity.Users;
import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    // 생성
    Post savePost(PostDto postDto, Users users);

    // 조회
    Post getPost(Long id);
    List<PostSummaryDto> getAllPosts();
    // offset 기반 페이징
    List<Post> findPostsByOffset(int page);
    // cursor 기반 페이징
    CursorDto<PostSummaryDto> findPostsByCursor(LocalDateTime localDateTime, Long cursor);
    CursorDto<PostSummaryDto> firstPostsByCursor();


    // 수정
    Post updatePost(Long id, PostDto postDto, Users users);

    // 삭제
    void deletePost(Long id, Users users);
}
