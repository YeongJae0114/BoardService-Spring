package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;

    @Override
    public void create(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAuthor(null);
        post.setCreatedDate(LocalDateTime.now());

        postRepository.save(post);
    }

    @Override
    public void update(Long postId, PostDto postDto) {

    }

    @Override
    public void delete(Long postId) {

    }

    @Override
    public PostDto getPost(Long postId) {
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
