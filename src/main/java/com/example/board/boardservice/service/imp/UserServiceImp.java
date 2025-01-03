package com.example.board.boardservice.service.imp;

import com.example.board.boardservice.dto.request.LoginRequest;
import com.example.board.boardservice.dto.request.SignUpRequest;
import com.example.board.boardservice.entity.Role;
import com.example.board.boardservice.entity.Users;
import com.example.board.boardservice.exception.CustomException;
import com.example.board.boardservice.repository.UserRepository;
import com.example.board.boardservice.response.model.ErrorCode;
import com.example.board.boardservice.session.SessionService;
import com.example.board.boardservice.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    @Override
    public Users signup(SignUpRequest signUpRequest){
        if (userRepository.existsByUsername(signUpRequest.getUsername())){
            throw new CustomException(ErrorCode.ACCOUNT_USERNAME_DUPLICATE,
                    "이미 존재하는 사용자 이름입니다.", Map.of("field", "username", "value",
                    signUpRequest.getUsername()));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())){
            throw new CustomException(
                    ErrorCode.ACCOUNT_EMAIL_DUPLICATE,
                    "이미 사용 중인 이메일입니다.",
                    Map.of("field", "email", "value", signUpRequest.getEmail()));
        }

        String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());

        new Users();
        Users signupUsers = Users.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encodePassword)
                .role(Role.USER)
                .build();

        return userRepository.save(signupUsers);
    }

    @Override
    public Users login(LoginRequest loginRequest, HttpSession httpSession) {
        Users users = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_CREDENTIALS, "사용자 이름 또는 이메일이 잘못되었습니다.", null));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
            throw new CustomException(
                    ErrorCode.INVALID_CREDENTIALS,
                    "비밀번호가 잘못되었습니다.",
                    null
            );
        }
        sessionService.saveUserToSession(httpSession, users);
        return users;
    }

}
