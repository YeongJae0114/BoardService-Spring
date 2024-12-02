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
        Pageable pageRequest = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Post> posts;
        if (createdDateCursor == null || cursorId == null) {
            // 첫 요청: 최신 데이터를 반환
            posts = postRepository.findAllByOrderByCreatedDateDesc(pageRequest);
        } else {
            // 커서 기반 요청: 특정 생성일 및 ID 이전 데이터 반환
            posts = postRepository.findPostsByCursor(createdDateCursor, cursorId, pageRequest);
        }

        // 다음 커서 정보 계산
        if (!posts.isEmpty()) {
            Post lastPost = posts.get(posts.size() - 1); // 마지막 데이터 기준으로 커서 설정
            return new CursorDto<>(
                    posts,
                    lastPost.getId(),
                    lastPost.getCreatedDate()
            );
        }

        // 결과가 없는 경우 빈 CursorDto 반환
        return new CursorDto<>(List.of(), null, null);
    }
}
