package com.example.board.boardservice.controller;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.response.ApiResponse;
import com.example.board.boardservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 등록
    @PostMapping(value = "/api/posts", produces = "application/json")
    public ApiResponse<Post>createPost(@RequestBody PostDto postDto){
        Post post = postService.createPost(postDto);
        return ApiResponse.makeResponse(post);
    }

    // 게시글 조회

    // 게시글 수정

    // 게시글 삭제

}
