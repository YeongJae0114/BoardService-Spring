package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.exception.CustomException;
import com.example.board.boardservice.repository.PostRepository;
import com.example.board.boardservice.response.model.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;

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
        return postRepository.findAll();
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
}
