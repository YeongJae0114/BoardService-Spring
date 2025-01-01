package com.example.board.boardservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy Loading 설정
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // 순환 참조 방지
    private Users author; // Users 객체와 연관 관계를 유지 (author로 변경)
}
