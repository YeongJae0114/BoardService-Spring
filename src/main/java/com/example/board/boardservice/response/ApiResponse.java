package com.example.board.boardservice.response;

import com.example.board.boardservice.dto.CursorDto;
import com.example.board.boardservice.entity.Post;
import com.example.board.boardservice.response.model.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final Status status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<T> results;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Metadata metaData;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    // 생성자
    // 정상 응답
    public ApiResponse(List<T> results) {
        this.status = new Status(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.results = results;

        // 결과가 2차원 리스트인지 확인하고 적절한 요소 개수 계산
        int totalCount = results.stream()
                .mapToInt(result -> result instanceof List ? ((List<?>) result).size() : 1)
                .sum();
        this.metaData = new Metadata(totalCount);
    }

    public ApiResponse(CursorDto<T> cursorDto) {
        this.status = new Status(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.results = cursorDto.getPostList();

        // 결과가 2차원 리스트인지 확인하고 적절한 요소 개수 계산
        int totalCount = results.stream()
                .mapToInt(result -> result instanceof List ? ((List<?>) result).size() : 1)
                .sum();
        this.metaData = new Metadata(totalCount, cursorDto.getNextCursorId(), cursorDto.getNextCreatedDateCursor(), cursorDto.isHasNext());
    }




    // 예외 응답
    public ApiResponse(int code, String message, Object data) {
        this.status = new Status(code, message);
        this.data = data;
    }


    @Getter
    @AllArgsConstructor
    private static class Status {
        private final int code;
        private final String message;
    }

    @Getter
    private static class Metadata {
        private final int resultCount;
        private Long nextCursorId;
        private LocalDateTime nextCreatedDateCursor;
        private boolean hasNext;

        public Metadata(int resultCount) {
            this.resultCount = resultCount;
        }

        public Metadata(int resultCount, Long nextCursorId, LocalDateTime nextCreatedDateCursor, boolean hasNext) {
            this.resultCount = resultCount;
            this.nextCursorId = nextCursorId;
            this.nextCreatedDateCursor = nextCreatedDateCursor;
            this.hasNext = hasNext;
        }
    }
}
