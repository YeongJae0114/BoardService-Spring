package com.example.board.boardservice.controller;

import com.example.board.boardservice.dto.CursorDto;
import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.entity.User;
import com.example.board.boardservice.response.ApiResponse;
import com.example.board.boardservice.response.model.ErrorCode;
import com.example.board.boardservice.service.PostService;
import com.example.board.boardservice.session.SessionService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final SessionService sessionService;

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
    @PostMapping(value = "/api/posts")
    public ApiResponse<Post>createPost(@RequestBody PostDto postDto, HttpSession session){
        User user = (User) sessionService.getUserFromSession(session);
        if (user == null){
            return new ApiResponse<>(ErrorCode.UNAUTHORIZED_ACCESS.getCode(), "로그인이 필요합니다.", null);
        }
        log.info("유효한 세션: {}", session.getId()); // 세션 로그 추가
        Post post = postService.createPost(postDto, user);
        return makeResponse(post);
    }


    // 게시글 수정
    @PutMapping("/api/posts/{id}")
    public ApiResponse<Post> updatePost(@PathVariable Long id, @RequestBody PostDto postDto, HttpSession session) {
        User user = (User) sessionService.getUserFromSession(session);
        if (user == null){
            return new ApiResponse<>(ErrorCode.UNAUTHORIZED_ACCESS.getCode(), "로그인이 필요합니다.", null);
        }
        Post post = postService.updatePost(id, postDto, user);
        return makeResponse(post);
    }

    // 게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id, HttpSession session) {
        User user = (User) sessionService.getUserFromSession(session);
        if (user == null){
            return new ApiResponse<>(ErrorCode.UNAUTHORIZED_ACCESS.getCode(), "로그인이 필요합니다.", null);
        }
        postService.deletePost(id, user);
        return makeResponse();
    }

    // 페이지네이션 적용 게시글 반환 offset 기반
    @GetMapping("/api/posts/paginated")
    public ApiResponse<List<Post>> getPaginatedPosts(@RequestParam(defaultValue = "0") int page){
        Instant start = Instant.now();
        List<Post> postsByOffset = postService.findPostsByOffset(page);
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();
        log.info("Pagination executed for page: {}, size: {}, execution time: {} ms", page, 10, timeElapsed);
        return makeResponse(Collections.singletonList(postsByOffset));
    }

    @GetMapping("/api/posts/cursor")
    public CursorDto<Post> getNextPage(@RequestParam(required = false) LocalDateTime createdDateCursor,
                                                    @RequestParam(required = false) Long cursorId){
        if (createdDateCursor == null && cursorId == null) {
            // 첫 번째 페이지 요청
            return postService.firstPostsByCursor();
        }

        if ((createdDateCursor == null && cursorId != null) || (createdDateCursor != null && cursorId == null)) {
            throw new IllegalArgumentException("createdDateCursor와 cursorId는 함께 제공되어야 합니다.");
        }

        // 다음 페이지 요청
        return postService.findPostsByCursor(createdDateCursor, cursorId);
    }



    // 게시글 모두 조회
    @GetMapping("/api/posts")
    public ApiResponse<List<Post>> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return makeResponse(Collections.singletonList(allPosts));
    }

    // 게시글 단건 조회
    @GetMapping("/api/posts/{id}")
    public ApiResponse<Post> getAllPosts(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return makeResponse(post);
    }

    }
