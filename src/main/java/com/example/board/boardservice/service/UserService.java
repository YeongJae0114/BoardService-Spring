package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.LoginRequest;
import com.example.board.boardservice.dto.SignUpRequest;
import com.example.board.boardservice.entity.Users;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    Users signup(SignUpRequest signUpRequest);
    Users login(LoginRequest loginRequest, HttpSession httpSession);

}
