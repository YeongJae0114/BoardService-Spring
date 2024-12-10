package com.example.board.boardservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursorDto<T> {
    private List<T> postList; // 현재 페이지의 데이터
    private Long nextCursorId; // 다음 커서 ID
    private LocalDateTime nextCreatedDateCursor; // 다음 커서 생성일
    private boolean hasNext; // 다음 페이지 여부
}
