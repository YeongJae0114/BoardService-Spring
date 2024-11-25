package com.example.board.boardservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CursorDto<T> {
    private List<T> data;                // 데이터 목록
    private Long nextCursorId;           // 다음 커서 ID
    private LocalDateTime nextCursorCreatedDate; // 다음 커서 생성일

}
