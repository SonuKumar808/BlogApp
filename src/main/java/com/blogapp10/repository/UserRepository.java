package com.blogapp10.repository;

import com.blogapp10.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
