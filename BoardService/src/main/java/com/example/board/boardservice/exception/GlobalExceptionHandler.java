package com.example.board.boardservice.exception;

import com.example.board.boardservice.response.ApiResponse;
import com.example.board.boardservice.response.model.ErrorCode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler <T>{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        // 필드별 에러 메시지 추출
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        // ApiResponse 객체 생성
        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                ErrorCode.ACCOUNT_USERNAME_DUPLICATE.getCode(),
                "유효성 검사 실패",
                errors
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException ex) {
        // CustomException에서 에러 코드, 메시지, 데이터를 가져옵니다.
        ErrorCode errorCode = ex.getErrorCode();
        String message = ex.getMessage();
        Object data = ex.getData();

        // ApiResponse를 생성합니다.
        ApiResponse<Object> response = new ApiResponse<>(
                errorCode.getCode(),
                message,
                data
        );

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}
