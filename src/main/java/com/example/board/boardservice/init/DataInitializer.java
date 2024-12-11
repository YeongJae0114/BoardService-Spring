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
    public CommandLineRunner initData(PostRepository postRepository) {
        return args -> {
            for (int i = 1; i <= 30; i++) {
                Post post = Post.builder()
                        .title("게시글 제목 " + i)
                        .content("게시글 내용 " + i + "입니다. 더미 데이터를 테스트하기 위해 작성되었습니다.")
                        .createdDate(LocalDateTime.now().minusDays(i)) // 생성 날짜를 i일 전으로 설정
                        .author(null) // 초기 데이터이므로 작성자 설정이 없을 경우 null로 설정
                        .build();

                postRepository.save(post);
            }
        };
    }
}
