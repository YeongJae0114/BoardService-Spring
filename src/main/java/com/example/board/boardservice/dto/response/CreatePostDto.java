package com.example.board.boardservice.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private String username;
}
