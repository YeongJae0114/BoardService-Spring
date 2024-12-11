package com.example.board.boardservice.session.imp;

import com.example.board.boardservice.session.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class MemorySessionServiceImp implements SessionService {
    private static final String USER_SESSION_KEY = "user";

    @Override
    public void saveUserToSession(HttpSession session, Object user) {
        session.setAttribute(USER_SESSION_KEY, user);
        log.info("{}", session);
    }

    @Override
    public Object getUserFromSession(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY);
    }

    @Override
    public void removeUserFromSession(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
}
