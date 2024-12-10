package com.example.board.boardservice.service.imp;

import com.example.board.boardservice.dto.CursorDto;
import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.entity.User;
import com.example.board.boardservice.exception.CustomException;
import com.example.board.boardservice.repository.PostRepository;
import com.example.board.boardservice.response.model.ErrorCode;
import com.example.board.boardservice.service.PostService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public Post createPost(PostDto postDto, User user) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(user)
                .createdDate(LocalDateTime.now())
                .build();
    }
    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SERVER_ERROR, "Post not found with id: " + id, id));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public Post updatePost(Long id, PostDto postDto, User user) {

        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SERVER_ERROR, "Post not found with id: " + id, id));

        if (!findPost.getAuthor().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS, "수정 권한이 없습니다.", null);
        }
        findPost.toBuilder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        // 수정일은 나중에 추가할게용
        return postRepository.save(findPost);
    }

    @Override
    public void deletePost(Long id, User user) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SERVER_ERROR, "Post not found with id: " + id, id));

        if (!findPost.getAuthor().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS, "삭제 권한이 없습니다.", null);
        }

        postRepository.deleteById(id);
    }

    // offset 기반 페이징 방식
    @Override
    public List<Post> findPostsByOffset(int page){
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by(Sort.Order.desc("createdDate"))); // 페이지 번호와 페이지 크기 설정
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.getContent(); // 실제 데이터 반환
    }

// cursor 기반 페이징 방식
    @Override
    public CursorDto<Post> findPostsByCursor(LocalDateTime createdDateCursor, Long cursorId) {
        // 커서 기반 페이징 처리
        Pageable pageRequest = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Post> posts = postRepository.findPostsByCursor(createdDateCursor, cursorId, pageRequest);

        // 다음 커서 정보 계산
        if (!posts.isEmpty()) {
            Post lastPost = posts.get(posts.size() - 1); // 마지막 데이터 기준으로 커서 설정
            boolean hasNext = posts.size() == DEFAULT_PAGE_SIZE; // 페이지 크기와 동일하면 다음 페이지가 있음

            return new CursorDto<>(
                    posts,
                    lastPost.getId(),
                    lastPost.getCreatedDate(),
                    hasNext
            );
        }
        // 결과가 없는 경우 빈 CursorDto 반환
        return new CursorDto<>(List.of(), null, null, false);
    }

    @Override
    public CursorDto<Post> firstPostsByCursor() {
        // 첫 번째 페이지의 데이터를 가져오는 로직
        List<Post> postList = postRepository.findTop10ByOrderByCreatedDateDesc();

        // 첫 번째와 마지막 데이터가 존재할 경우 커서와 생성일 정보를 설정
        if (!postList.isEmpty()) {
            Post firstPost = postList.get(0);  // 첫 번째 데이터
            Post lastPost = postList.get(postList.size() - 1); // 마지막 데이터

            return new CursorDto<>(
                    postList,
                    lastPost.getId(),
                    lastPost.getCreatedDate(),
                    true  // 첫 페이지이므로 기본적으로 다음 페이지가 있음
            );
        }

        // 결과가 없으면 빈 CursorDto 반환
        return new CursorDto<>(List.of(), null, null, false);
    }

}
