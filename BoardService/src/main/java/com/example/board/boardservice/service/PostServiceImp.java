package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.exception.CustomException;
import com.example.board.boardservice.repository.PostRepository;
import com.example.board.boardservice.response.model.ErrorCode;
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
    private final int size = 10;
    private final LocalDateTime MAX_TIME = LocalDateTime.MAX;
    @Override
    public Post createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setCreatedDate(LocalDateTime.now());

        return postRepository.save(post);
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
    public Post updatePost(Long id, PostDto postDto) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SERVER_ERROR, "Post not found with id: " + id, id));

        findPost.setTitle(postDto.getTitle());
        findPost.setAuthor(postDto.getAuthor());
        findPost.setContent(postDto.getContent());

        // 수정일은 나중에 추가할게용
        return postRepository.save(findPost);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // offset 기반 페이징 방식
    @Override
    public List<Post> findPostsByOffset(int page){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate"))); // 페이지 번호와 페이지 크기 설정
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.getContent(); // 실제 데이터 반환
    }

    //cursor 기반 페이징 방식
    @Override
    public List<Post> findPostsByCursor(int cursorId) {
        Pageable pageable = PageRequest.of(cursorId, size, Sort.by(Sort.Order.desc("createdDate"))); // 페이지 번호와 페이지 크기 설정
        if (cursorId==0){
            return postRepository.findByCreatedDateBeforeOrderByCreatedDateDesc(MAX_TIME, pageable);
        }else {
            return null;
        }
    }
}
