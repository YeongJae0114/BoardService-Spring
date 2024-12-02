package com.example.board.boardservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Comment {
    @Id @GeneratedValue
    private Long id;
}
