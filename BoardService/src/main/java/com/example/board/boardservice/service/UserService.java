package com.example.board.boardservice.service;

import com.example.board.boardservice.dto.LoginDto;
import com.example.board.boardservice.dto.SignUpRequest;
import com.example.board.boardservice.entity.User;

public interface UserService {
    User signup(SignUpRequest signUpRequest);
    User login(LoginDto loginDto);
    void session();
    void logout();
}
