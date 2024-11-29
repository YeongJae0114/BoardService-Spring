package com.example.board.boardservice.response;

import com.example.board.boardservice.response.model.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
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

        public Metadata(int resultCount) {
            this.resultCount = resultCount;
        }
    }
}
