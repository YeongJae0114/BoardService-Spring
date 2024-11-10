package com.example.board.boardservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Post {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
}
