package com.example.board.boardservice.controller;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.response.ApiResponse;
import com.example.board.boardservice.service.PostService;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedModel.PageMetadata;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    // 페이지네이션 적용 게시글 반환
    @GetMapping("/api/posts/paginated")
    public ApiResponse<List<Post>> getPaginatedPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "100") int size) {
        Instant start = Instant.now();
        List<Post> postsByOffset = postService.getPostsByOffset(page, size);
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();
        log.info("Pagination executed for page: {}, size: {}, execution time: {} ms", page, size, timeElapsed);
        return makeResponse(Collections.singletonList(postsByOffset));
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
