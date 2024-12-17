package com.example.board.boardservice.repository;

import com.example.board.boardservice.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
}
