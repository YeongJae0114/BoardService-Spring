package com.example.board.boardservice.init;

import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.repository.PostRepository;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(PostRepository postRepository){
        return args -> {
            for (int i = 1; i <= 100; i++) {
                Post post = new Post();
                post.setId((long) i);
                post.setTitle("Post " + i);
                post.setContent("This is the content of post " + i + ".");
                post.setAuthor("Author " + i);
                post.setCreatedDate(LocalDateTime.now());
                postRepository.save(post);
            }
        };
    }
}
