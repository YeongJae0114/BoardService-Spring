package com.example.board.boardservice.response.model;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 성공 코드
    OK(2000, "OK"),

    // 클라이언트 오류
    BAD_REQUEST(4000, "Bad Request"),
    UNAUTHORIZED(4010, "Unauthorized"),
    FORBIDDEN(4030, "Forbidden"),
    NOT_FOUND(4040, "Resource Not Found"),

    // 서버 오류
    INTERNAL_SERVER_ERROR(5000, "Internal Server Error"),
    SERVICE_UNAVAILABLE(5030, "Service Unavailable");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
