package com.example.board.boardservice.response.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    OK(2000, "OK", HttpStatus.OK),
    SERVER_ERROR(5001, "SERVER ERROR!", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(500, "BAD REQUEST", HttpStatus.OK),
    // 로그인 관련 오류
    INVALID_CREDENTIALS(4001, "사용자 이름 또는 비밀번호가 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED(4002, "계정이 잠겨 있습니다. 관리자에게 문의하세요.", HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED(4003, "비활성화된 계정입니다. 관리자에게 문의하세요.", HttpStatus.FORBIDDEN),
    ACCOUNT_NOT_FOUND(4004, "존재하지 않는 계정입니다.", HttpStatus.NOT_FOUND),
    PASSWORD_EXPIRED(4005, "비밀번호가 만료되었습니다. 비밀번호를 변경하세요.", HttpStatus.FORBIDDEN),
    TOO_MANY_ATTEMPTS(4006, "로그인 시도가 너무 많습니다. 잠시 후 다시 시도하세요.", HttpStatus.TOO_MANY_REQUESTS),
    SESSION_EXPIRED(4007, "세션이 만료되었습니다. 다시 로그인하세요.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_ACCESS(4008, "인증되지 않은 접근입니다.", HttpStatus.UNAUTHORIZED),

    // 회원가입 관련 오류
    ACCOUNT_USERNAME_DUPLICATE(4010, "이미 사용 중인 사용자 이름입니다.", HttpStatus.CONFLICT),
    ACCOUNT_EMAIL_DUPLICATE(4011, "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT);



    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
