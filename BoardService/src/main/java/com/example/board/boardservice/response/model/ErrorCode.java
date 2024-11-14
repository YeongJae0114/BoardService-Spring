package com.example.board.boardservice.response.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    OK(2000, "OK", HttpStatus.OK),
    SERVER_ERROR(5001, "SERVER ERROR!", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(500, "BAD REQUEST", HttpStatus.OK)
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
