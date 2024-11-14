package com.example.board.boardservice.exception;

import com.example.board.boardservice.response.model.ErrorCode;
import java.util.AbstractMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class CustomException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;
    private String message;
    @Getter
    private Map.Entry<String, Object> data;

    // 생성자
    public CustomException(ErrorCode errorCode, String message, Object data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = new AbstractMap.SimpleEntry<>(data.getClass().getSimpleName(), data);
    }

    public String getMessage(){
        // CustomException으로 넘어오는 message가 있으면 그걸 쓰고, 없으면, errorCode의 기본 메세지를 리턴한다.
        if(StringUtils.hasLength(this.message)) {
            return this.message;
        }
        return errorCode.getMessage();
    }

}

