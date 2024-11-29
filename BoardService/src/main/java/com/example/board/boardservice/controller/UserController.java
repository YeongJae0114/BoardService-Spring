package com.example.board.boardservice.controller;

import com.example.board.boardservice.dto.SignUpRequest;
import com.example.board.boardservice.response.ApiResponse;
import com.example.board.boardservice.response.model.ErrorCode;
import com.example.board.boardservice.service.UserService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/auth/signup")
    public ApiResponse<Object> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        userService.signup(signUpRequest);
        return new ApiResponse<>(ErrorCode.OK.getCode(), "회원가입 성공", null);
    }

    @PostMapping("/api/auth/login")
    public void login(){

    }


}
