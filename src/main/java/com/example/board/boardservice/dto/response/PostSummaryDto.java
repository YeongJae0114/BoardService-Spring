package com.example.board.boardservice.dto.response;

import java.time.LocalDateTime;


public interface PostSummaryDto {
    Long getId();
    String getTitle();
    String getContent();
    LocalDateTime getCreatedDate();
    String getAuthor();
}
