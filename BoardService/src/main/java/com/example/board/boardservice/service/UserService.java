package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.LoginRequest;
import com.example.board.boardservice.dto.SignUpRequest;
import com.example.board.boardservice.entity.User;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    User signup(SignUpRequest signUpRequest);
    User login(LoginRequest loginRequest, HttpSession httpSession);
    void session();
    void logout();
}
