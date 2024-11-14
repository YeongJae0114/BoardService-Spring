package com.example.board.boardservice.controller;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.response.ApiResponse;
import com.example.board.boardservice.service.PostService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    public <T> ApiResponse<T> makeResponse(List<T>result){
        return new ApiResponse<>(result);
    }

    public <T> ApiResponse<T> makeResponse(T result){
        return makeResponse(Collections.singletonList(result));
    }
    // 빈 응답 생성 메서드
    public <T> ApiResponse<T> makeResponse() {
        return new ApiResponse<>(Collections.emptyList());
    }


    // 게시글 등록
    @PostMapping(value = "/api/posts", produces = "application/json")
    public ApiResponse<Post>createPost(@RequestBody PostDto postDto){
        Post post = postService.createPost(postDto);
        return makeResponse(post);
    }

    // 게시글 단건 조회
    @GetMapping("/api/posts")
    public ApiResponse<List<Post>> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return makeResponse(Collections.singletonList(allPosts));
    }

    // 게시글 모두 조회
    @GetMapping("/api/posts/{id}")
    public ApiResponse<Post> getAllPosts(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return makeResponse(post);
    }

    // 게시글 수정
    @PutMapping("/api/posts/{id}")
    public ApiResponse<Post> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        Post post = postService.updatePost(id, postDto);
        return makeResponse(post);
    }

    // 게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return makeResponse();
    }

    }
