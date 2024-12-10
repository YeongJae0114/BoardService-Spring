package com.example.board.boardservice.controller;

import com.example.board.boardservice.dto.LoginRequest;
import com.example.board.boardservice.dto.SignUpRequest;
import com.example.board.boardservice.entity.User;
import com.example.board.boardservice.response.ApiResponse;
import com.example.board.boardservice.response.model.ErrorCode;
import com.example.board.boardservice.service.UserService;
import com.example.board.boardservice.session.SessionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping("/api/auth/session")
    public ApiResponse<Object> checkSession(HttpSession httpSession){
        User user = (User) sessionService.getUserFromSession(httpSession);
        if (user == null){
            return new ApiResponse<>(ErrorCode.SESSION_EXPIRED.getCode(), "세션이 유효하지 않거나 데이터가 없습니다.", null);
        }
        return new ApiResponse<>(ErrorCode.OK.getCode(), "세션 확인 성공", user.getUsername());
    }

    @PostMapping("/api/auth/signup")
    public ApiResponse<Object> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        userService.signup(signUpRequest);
        return new ApiResponse<>(ErrorCode.OK.getCode(), "회원가입 성공", null);
    }

    @PostMapping("/api/auth/login")
    public ApiResponse<Object> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession httpSession){
        User login = userService.login(loginRequest, httpSession);

        return new ApiResponse<>(ErrorCode.OK.getCode(), "로그인 성공", login.getUsername());
    }

    @PostMapping("/api/auth/logout")
    public ApiResponse<Object> logout(HttpSession httpSession){
        sessionService.removeUserFromSession(httpSession);
        return new ApiResponse<>(ErrorCode.OK.getCode(), "로그아웃 성공", null);
    }


}
