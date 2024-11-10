package com.example.board.boardservice.response;

import com.example.board.boardservice.response.model.ErrorCode;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final Status status;
    private final MetaData metaData;
    private final List<T> result;
    private final Object data;

    // Private 생성자
    private ApiResponse(Status status, MetaData metaData, List<T> result, Object data) {
        this.status = status;
        this.metaData = metaData;
        this.result = result;
        this.data = data;
    }

    // 단일 결과 응답 생성 메서드
    public static <T> ApiResponse<T> makeResponse(T result) {
        return new ApiResponse<>(
                new Status(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage()),
                new MetaData(1),
                List.of(result),
                null
        );
    }

    // 리스트 결과 응답 생성 메서드
    public static <T> ApiResponse<T> makeResponse(List<T> results) {
        return new ApiResponse<>(
                new Status(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage()),
                new MetaData(results.size()),
                results,
                null
        );
    }

    // 에러 응답 생성 메서드
    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message, Object errorData) {
        return new ApiResponse<>(
                new Status(errorCode.getCode(), message != null ? message : errorCode.getMessage()),
                new MetaData(0),
                Collections.emptyList(),
                errorData
        );
    }

    @Getter
    @AllArgsConstructor
    private static class Status {
        private final int code;
        private final String message;
    }

    @Getter
    @AllArgsConstructor
    private static class MetaData {
        private final int resultCount;
    }
}
